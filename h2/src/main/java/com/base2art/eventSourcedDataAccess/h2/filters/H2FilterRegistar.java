package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.dataTypes.DataType;
import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeRegistrar;
import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeRegistrarQueries;

import java.util.HashMap;
import java.util.Map;

public class H2FilterRegistar {

    private static final Map<DataType<?>, H2Filter<?, ?>> map;

    static {
        map = new HashMap<>();
        register(DataTypeRegistrar.Integer, new IntH2Filter());
        register(DataTypeRegistrar.Boolean, new BooleanH2Filter());
        register(DataTypeRegistrar.Byte, new ByteH2Filter());
        register(DataTypeRegistrar.Short, new ShortH2Filter());
        register(DataTypeRegistrar.Long, new LongH2Filter());
        register(DataTypeRegistrar.Decimal, new DecimalH2Filter());
        register(DataTypeRegistrar.Double, new DoubleH2Filter());
        register(DataTypeRegistrar.Float, new FloatH2Filter());
        register(DataTypeRegistrar.LocalTime, new LocalTimeH2Filter());
        register(DataTypeRegistrar.LocalDate, new LocalDateH2Filter());
        register(DataTypeRegistrar.Instant, new InstantH2Filter());
        register(DataTypeRegistrar.Other, new NullH2Filter<>());
        register(DataTypeRegistrar.String, new StringH2Filter());
        register(DataTypeRegistrar.ByteArray, new NullH2Filter<>());
        register(DataTypeRegistrar.UUID, new UUIDH2Filter());
    }

    public static RawH2Filter[] values() {
        return map.values().toArray(new RawH2Filter[map.size()]);
    }

    private static <T> H2Filter<T, ?> register(final DataType<T> type, final H2Filter<T, ?> dataFilterBase) {
        map.put(type, dataFilterBase);
        return dataFilterBase;
    }

    public static RawH2Filter getFilter(final Class<?> dataType) {

        return DataTypeRegistrarQueries.getByClass(map, dataType, NullH2Filter::new);
    }
}
