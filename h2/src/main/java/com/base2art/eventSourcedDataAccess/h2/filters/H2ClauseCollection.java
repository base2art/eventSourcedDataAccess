package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.h2.utils.PositionedSqlSetter;
import lombok.Data;
import lombok.val;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class H2ClauseCollection {

    private List<Setup> items = new ArrayList<>();

    public int length() {
        return this.items.size();
    }

    public void add(int parameterCount, String clause, PositionedSqlSetter setupMethod) {
        this.items.add(new Setup(parameterCount, clause, setupMethod));
    }

    public String join(final String separator) {
        val joiner = new StringJoiner(separator);

        this.items.stream()
                  .map(Setup::getClause)
                  .forEach(joiner::add);

        return joiner.toString();
    }

    public int setParameters(PreparedStatement statement, int baseCounter) throws SQLException {

        for (int i = 0; i < this.items.size(); i++) {

            Setup s = this.items.get(i);
            s.setupMethod.accept(statement, baseCounter);

            baseCounter += s.getParameterCount();
        }

        return baseCounter;
    }

    @Data
    private class Setup {
        private final int parameterCount;
        private final String clause;
        private final PositionedSqlSetter setupMethod;
    }
}
