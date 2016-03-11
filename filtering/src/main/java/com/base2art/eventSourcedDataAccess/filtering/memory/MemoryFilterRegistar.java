package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.dataTypes.DataType;
import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeRegistrar;
import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeRegistrarQueries;

import java.util.HashMap;
import java.util.Map;

public class MemoryFilterRegistar {

    private static final Map<DataType<?>, DataFilter<?, ?>> map;

    public static final DataFilter<Object, ?> Other;

    static {
        map = new HashMap<>();
        register(DataTypeRegistrar.Integer, new IntDataFilter());
        register(DataTypeRegistrar.Boolean, new BooleanDataFilter());
        register(DataTypeRegistrar.Byte, new ByteDataFilter());
        register(DataTypeRegistrar.Short, new ShortDataFilter());
        register(DataTypeRegistrar.Long, new LongDataFilter());
        register(DataTypeRegistrar.Decimal, new DecimalDataFilter());
        register(DataTypeRegistrar.Double, new DoubleDataFilter());
        register(DataTypeRegistrar.Float, new FloatDataFilter());
        register(DataTypeRegistrar.LocalTime, new LocalTimeDataFilter());
        register(DataTypeRegistrar.LocalDate, new LocalDateDataFilter());
        register(DataTypeRegistrar.Instant, new InstantDataFilter());
        Other = register(DataTypeRegistrar.Other, new NullDataFilter<>());
        register(DataTypeRegistrar.String, new StringDataFilter());
        register(DataTypeRegistrar.ByteArray, new NullDataFilter<>());
        register(DataTypeRegistrar.UUID, new UUIDDataFilter());
    }

    public static RawDataFilter[] values() {
        return map.values().toArray(new RawDataFilter[map.size()]);
    }

    private static <T> DataFilter<T, ?> register(final DataType<T> type, final DataFilter<T, ?> dataFilterBase) {
        map.put(type, dataFilterBase);
        return dataFilterBase;
    }

    public static RawDataFilter getFilter(final Class<?> dataType) {

        return DataTypeRegistrarQueries.getByClass(map, dataType, () -> Other);
    }
}
