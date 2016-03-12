package com.base2art.eventSourcedDataAccess.conventional;

import com.base2art.eventSourcedDataAccess.EnumSortParser;
import com.base2art.eventSourcedDataAccess.StreamOrderer;
import com.base2art.eventSourcedDataAccess.utils.Reflection;
import lombok.val;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.stream.Stream;

public class FieldEnumOrderer<ObjectEntity, OrderOptions extends Enum<OrderOptions>>
        implements StreamOrderer<ObjectEntity, OrderOptions> {

    private final Class<ObjectEntity> entityClass;

    public FieldEnumOrderer(Class<ObjectEntity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Stream<ObjectEntity> order(final Stream<ObjectEntity> stream, final OrderOptions orderOptions) {
        if (orderOptions.getClass().isEnum()) {

            val sortInformation = EnumSortParser.parse(orderOptions);

            Optional<Field> field = Reflection.fields(this.entityClass)
                                              .filter(x -> x.getName().equalsIgnoreCase(sortInformation.getFieldName()))
                                              .findFirst();

            if (!field.isPresent()) {
                return stream;
            }

            final Field fieldObj = field.get();

            final boolean isAscending = sortInformation.isAscending();
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
