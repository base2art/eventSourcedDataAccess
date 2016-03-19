package com.base2art.eventSourcedDataAccess.h2.utils;

import com.base2art.eventSourcedDataAccess.filtering.utils.FilterReader;
import com.base2art.eventSourcedDataAccess.h2.filters.H2ClauseCollection;
import com.base2art.eventSourcedDataAccess.h2.filters.H2FilterRegistar;
import com.base2art.eventSourcedDataAccess.h2.filters.RawH2Filter;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@UtilityClass
public class SqlBuilder {

    public static <FilterOptions> void process(
            final FilterOptions filterOptions,
            final String tableAlias,
            final List<Field> fields,
            final H2ClauseCollection collection) {

        Map<String, Object> map = FilterReader.getFilters(filterOptions);

        for (Field field : fields) {
            if (map.containsKey(field.getName())) {

                RawH2Filter rawType = H2FilterRegistar.getFilter(field.getType());

                rawType.handleRaw(
                        tableAlias + "." + field.getName(),
                        map.get(field.getName()),

                        collection);
            }
        }
    }
}
