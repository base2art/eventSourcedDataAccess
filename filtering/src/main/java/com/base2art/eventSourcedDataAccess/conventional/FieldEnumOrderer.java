package com.base2art.eventSourcedDataAccess.conventional;

import com.base2art.eventSourcedDataAccess.StreamOrderer;
import com.base2art.eventSourcedDataAccess.utils.Reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class FieldEnumOrderer<ObjectEntity, OrderOptions>
        implements StreamOrderer<ObjectEntity, OrderOptions> {

    private final Class<ObjectEntity> entityClass;

    public FieldEnumOrderer(Class<ObjectEntity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Stream<ObjectEntity> order(final Stream<ObjectEntity> stream, final OrderOptions orderOptions) {
        if (orderOptions.getClass().isEnum()) {

            String fieldName = orderOptions.toString();

            boolean asc = true;
            Map<String, Boolean> items = new HashMap<>();

            items.put("ASC", true);
            items.put("ASCENDING", true);
            items.put("DESCENDING", false);
            items.put("DESC", false);

            for (Map.Entry<String, Boolean> entry : items.entrySet()) {

                if (fieldName.toUpperCase().endsWith(entry.getKey())) {
                    asc = true;
                    fieldName = fieldName.substring(0, fieldName.length() - entry.getKey().length());
                    break;
                }
            }

            final String newFieldName = fieldName;

            Optional<Field> field = Reflection.fields(this.entityClass)
                                              .filter(x -> x.getName().equalsIgnoreCase(newFieldName))
                                              .findFirst();

            if (!field.isPresent()) {
                return stream;
            }

            final Field fieldObj = field.get();

            final boolean isAscending = asc;
            fieldObj.setAccessible(true);
            return stream.sorted((o1, o2) -> isAscending ? compareItem(o1, o2, fieldObj) : compareItem(o2, o1, fieldObj));
        }
        return stream;
    }

    private int compareItem(final ObjectEntity x, final ObjectEntity y, Field field) {

        try {
            Comparable tX = (Comparable) field.get(x);

            Comparable tY = (Comparable) field.get(y);

            return tX.compareTo(tY);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
