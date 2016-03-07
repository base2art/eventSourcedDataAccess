package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.dataTypes.DataType;
import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeRegistar;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

public class MemoryFilterRegistar {

    private static final Map<DataType<?>, DataFilter<?>> map;

    public static final DataFilter<Object> Other;

    static {
        map = new HashMap<>();
        register(DataTypeRegistar.Integer, new IntDataFilter());
        register(DataTypeRegistar.Boolean, new BooleanDataFilter());
        register(DataTypeRegistar.Byte, new ByteDataFilter());
        register(DataTypeRegistar.Short, new ShortDataFilter());
        register(DataTypeRegistar.Long, new LongDataFilter());
        register(DataTypeRegistar.Decimal, new DecimalDataFilter());
        register(DataTypeRegistar.Double, new DoubleDataFilter());
        register(DataTypeRegistar.Float, new FloatDataFilter());
        register(DataTypeRegistar.LocalTime, new LocalTimeDataFilter());
        register(DataTypeRegistar.LocalDate, new LocalDateDataFilter());
        register(DataTypeRegistar.Instant, new InstantDataFilter());
        Other = register(DataTypeRegistar.Other, new NullDataFilter<>());
        register(DataTypeRegistar.String, new StringDataFilter());
        register(DataTypeRegistar.ByteArray, new NullDataFilter<>());
        register(DataTypeRegistar.UUID, new UUIDDataFilter());
    }

    public static DataFilter<?>[] values() {
        return map.values().toArray(new DataFilter[map.size()]);
    }

    private static <T> DataFilter<T> register(final DataType<T> type, final DataFilter<T> dataFilterBase) {
        map.put(type, dataFilterBase);
        return dataFilterBase;
    }

    public static DataFilter<?> getFilter(final Class<?> dataType) {
        for (Map.Entry<DataType<?>, DataFilter<?>> typeEntry : map.entrySet()) {

            val type = typeEntry.getKey();

            Class<?> primitiveType = type.getPrimitiveClass();
            Class<?> nonPrimitiveType = type.getNonPrimitiveClass();
            if (primitiveType != null && primitiveType == dataType) {
                return typeEntry.getValue();
            }

            if (nonPrimitiveType != null && nonPrimitiveType == dataType) {
                return typeEntry.getValue();
            }
        }

        return Other;
    }
}
