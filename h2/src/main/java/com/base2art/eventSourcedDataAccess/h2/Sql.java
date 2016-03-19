package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.h2.filters.H2ClauseCollection;
import com.base2art.eventSourcedDataAccess.h2.utils.ResourceReader;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.lang.reflect.Field;
import java.util.List;

@UtilityClass
public class Sql {

    public static <Id> String paged(
            final H2Connector<Id> connector,
            final Id marker,
            final List<Field> objectFields,
            final String sortField,
            final boolean isAscending,
            final H2ClauseCollection objectJoiner,
            final H2ClauseCollection versionJoiner) {

        String sql =
                marker == null
                ? ResourceReader.readStringUnchecked("/com/base2art/eventSourcedDataAccess/h2/sql/paged-first.sql", loader())
                : ResourceReader.readStringUnchecked("/com/base2art/eventSourcedDataAccess/h2/sql/paged.sql", loader());

        boolean isSortOnObject = objectFields.stream()
                                             .filter(x -> sortField.equalsIgnoreCase(x.getName()))
                                             .findAny()
                                             .isPresent();

        final String objectClause = objectJoiner == null || objectJoiner.length() == 0 ? "" : " AND " + objectJoiner.join(" AND ");
        final String versionClause = versionJoiner == null || versionJoiner.length() == 0 ? "" : " AND " + versionJoiner.join(" AND ");

        return sql.replace("{objectTableName}", connector.objectTable())
                  .replace("{objectVersionTableName}", connector.objectVersionTable())
                  .replace("{sortColumnTable[ot|p1]}", isSortOnObject ? "ot" : "p1")
                  .replace("{sortColumnTable[pj|po1]}", isSortOnObject ? "pj" : "po1")
                  .replace("{sortColumn}", sortField)
                  .replace("{objectClause}", objectClause)
                  .replace("{objectVersionClause}", versionClause)
                  .replace("{sortDirection}", isAscending ? "ASC" : "DESC");
    }

    public static <Id> String filtered(
            final H2Connector<Id> connector,
            final H2ClauseCollection objectJoiner,
            final H2ClauseCollection versionJoiner) {

        final String versionClause = versionJoiner.length() == 0 ? "" : " AND " + versionJoiner.join(" AND ");
        final String objectClause = objectJoiner.length() == 0 ? "" : " AND " + objectJoiner.join(" AND ");

        val sql = ResourceReader.readStringUnchecked("/com/base2art/eventSourcedDataAccess/h2/sql/filtered.sql", loader());

        return sql.replace("{objectTableName}", connector.objectTable())
                  .replace("{objectVersionTableName}", connector.objectVersionTable())
                  .replace("{objectVersionTableName}", connector.objectVersionTable())

                  .replace("{objectClause}", objectClause)
                  .replace("{objectVersionClause}", versionClause);
    }

    public static <Id> String latestVersionByObjectId(final H2Connector<Id> connector) {

        val sql = ResourceReader.readStringUnchecked("/com/base2art/eventSourcedDataAccess/h2/sql/latest-version-by-object-id.sql", loader());

        return sql.replace("{objectTableName}", connector.objectTable())
                  .replace("{objectVersionTableName}", connector.objectVersionTable());
    }

    private static ClassLoader loader() {
        return Sql.class.getClassLoader();
    }
}
