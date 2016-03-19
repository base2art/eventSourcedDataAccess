package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.h2.parameters.RawH2Type;
import com.base2art.eventSourcedDataAccess.h2.utils.ParameterSetter;
import lombok.val;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public final class H2Queries {

    private H2Queries() {}

    public static <Id, T> Optional<T> fetchSingleObject(
            final H2Connector<Id> connector,
            final Id id,
            final String sql,
            final List<Field> fields,
            final Function<Id, T> fetcher)
            throws DataAccessReaderException {

        try (Connection connection = connector.openConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                val type = connector.idH2Type();
                type.setParameter(statement, 1, id);

                try (ResultSet set = statement.executeQuery()) {

                    if (set.next()) {
                        T data = fetcher.apply(id);
                        populateData(connector, fields, data, set);
                        return Optional.of(data);
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessReaderException(e);
        }

        return Optional.empty();
    }

    public static <Id, T> Map<Id, T> fetchObjectMap(
            final H2Connector<Id> connector,
            final String sql,
            final ParameterSetter parameterSetter,
            final List<Field> fields,
            final Function<Id, T> fetcher)
            throws DataAccessReaderException {

        Map<Id, T> items = new LinkedHashMap<>();
        try (Connection connection = connector.openConnection()) {

            try (PreparedStatement statement = connection.prepareStatement(sql)) {

                if (parameterSetter != null) {
                    parameterSetter.accept(statement);
                }

                try (ResultSet set = statement.executeQuery()) {

                    val type = connector.idH2Type();
                    while (set.next()) {
                        Id fetchedId = type.getConvertedParameter(set, "object_id");
                        T data = fetcher.apply(fetchedId);
                        populateData(connector, fields, data, set);
                        items.put(fetchedId, data);
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new DataAccessReaderException(e);
        }

        return items;
    }

    public static <Id, T> void populateData(
            final H2Connector<Id> connector,
            final List<Field> fields,
            final T objectData,
            final ResultSet set) throws DataAccessReaderException, SQLException {

        for (Field field : fields) {
            RawH2Type type = connector.getTypeByField(field);
            field.setAccessible(true);

            try {
                field.set(objectData, type.getParameter(set, field.getName()));
            }
            catch (IllegalAccessException e) {
                throw new DataAccessReaderException(e);
            }
        }
    }
}
