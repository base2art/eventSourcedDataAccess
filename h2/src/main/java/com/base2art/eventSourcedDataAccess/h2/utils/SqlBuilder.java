package com.base2art.eventSourcedDataAccess.h2.utils;

import com.base2art.eventSourcedDataAccess.filtering.utils.FilterReader;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@UtilityClass
public class SqlBuilder {

    public static <FilterOptions> void process(
            final FilterOptions filterOptions,
            final StringJoiner objectJoiner,
            final List<Field> fields) {

        Map<String, Object> map = FilterReader.getFilters(filterOptions);

        for (Field field : fields) {
            if (map.containsKey(field.getName())) {
                objectJoiner.add(getClause(field.getName(), field.getType(), map.get(field.getName())));
            }
        }
    }

    private static String getClause(final String name, Class<?> fieldType, final Object filter) {

        val type = H2TypeRegistrar.getTypeByField(fieldType);

//        if (type.getNonPrimitiveClass())

        return type.getTypeName();
    }
}
