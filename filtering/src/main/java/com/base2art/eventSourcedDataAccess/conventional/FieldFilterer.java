package com.base2art.eventSourcedDataAccess.conventional;

import com.base2art.eventSourcedDataAccess.StreamFilterer;
import com.base2art.eventSourcedDataAccess.filtering.dataTypes.MemoryFilterRegistar;
import com.base2art.eventSourcedDataAccess.filtering.dataTypes.DataFilter;
import com.base2art.eventSourcedDataAccess.filtering.utils.FilterReader;
import com.base2art.eventSourcedDataAccess.utils.Reflection;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

public class FieldFilterer<ObjectEntity, FilterOptions>
        implements StreamFilterer<ObjectEntity, FilterOptions> {

    @Override
    public Stream<ObjectEntity> filter(final Stream<ObjectEntity> stream, final FilterOptions filterOptions) {

        if (filterOptions == null) {
            return stream;
        }

        Map<String, Object> map = FilterReader.getFilters(filterOptions);

        return stream.filter(x -> {

            if (x == null) {
                return false;
            }

            try {

                Field[] fields = Reflection.getAllFields(x.getClass());
                for (Field field : fields) {

                    if (map.containsKey(field.getName())) {

                        field.setAccessible(true);

                        Object obj = field.get(x);
                        if (obj == null) {
                            continue;
                        }

                        DataFilter<?> dataFilter = MemoryFilterRegistar.getFilter(obj.getClass());
                        if (dataFilter == null) {
                            continue;
                        }

                        if (!dataFilter.handleRaw(obj, map.get(field.getName()))) {
                            return false;
                        }
                    }
                }
            }
            catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            return true;
        });
    }
}
