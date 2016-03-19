package com.base2art.eventSourcedDataAccess.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.*;
import com.base2art.eventSourcedDataAccess.filtering.fields.*;

import java.util.ArrayList;

public class DataTypeFieldRegistrar {

    private static final ArrayList<DataTypeField<?>> map;

    static {
        map = new ArrayList<>();
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Integer, IntNumberFilterField.class, DefaultIntNumberFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Boolean, BooleanFilterField.class, DefaultBooleanFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Byte, ByteNumberFilterField.class, DefaultByteNumberFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Short, ShortNumberFilterField.class, DefaultShortNumberFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Long, LongNumberFilterField.class, DefaultLongNumberFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Decimal, DecimalNumberFilterField.class, DefaultDecimalNumberFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Double, DoubleNumberFilterField.class, DefaultDoubleNumberFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Float, FloatNumberFilterField.class, DefaultFloatNumberFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.LocalTime, TimeFilterField.class, DefaultTimeFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.LocalDate, DateFilterField.class, DefaultDateFilterField.class));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Instant, DateTimeFilterField.class, DefaultDateTimeFilterField.class));
//        register(new DataTypeFieldImpl<>(DataTypeRegistrar.Other, null, null));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.String, TextFilterField.class, DefaultTextFilterField.class));
//        register(new DataTypeFieldImpl<>(DataTypeRegistrar.ByteArray, null, null));
        register(new DataTypeFieldImpl<>(DataTypeRegistrar.UUID, UUIDFilterField.class, DefaultUUIDFilterField.class));
    }

    public static DataTypeField<?>[] values() {
        return map.toArray(new DataTypeField[map.size()]);
    }

    public static boolean hasType(Class<?> type) {
        return map.stream()
                  .filter(x -> x.getDataType().getNonPrimitiveClass() == type || x.getDataType().getPrimitiveClass() == type)
                  .findFirst()
                  .isPresent();
    }

    @SuppressWarnings("unchecked")
    public static DataTypeField<?> getField(final Class<?> type) {
        return map.stream()
                  .filter(x -> x.getDataType().getNonPrimitiveClass() == type || x.getDataType().getPrimitiveClass() == type)
                  .findFirst()
                  .orElse(null);
    }

    private static <T> DataTypeField<T> register(final DataTypeField<T> dataTypeBase) {
        map.add(dataTypeBase);
        return dataTypeBase;
    }
}
