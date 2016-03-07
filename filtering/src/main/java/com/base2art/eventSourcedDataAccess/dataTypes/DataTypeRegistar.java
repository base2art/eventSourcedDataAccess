package com.base2art.eventSourcedDataAccess.dataTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

public class DataTypeRegistar {

    private static final ArrayList<DataType<?>> map;

    public static final DataType<Object> Other;
    public static final DataType<Integer> Integer;
    public static final DataType<Boolean> Boolean;
    public static final DataType<Byte> Byte;
    public static final DataType<Short> Short;
    public static final DataType<Long> Long;
    public static final DataType<BigDecimal> Decimal;
    public static final DataType<Double> Double;
    public static final DataType<Float> Float;
    public static final DataType<LocalTime> LocalTime;
    public static final DataType<LocalDate> LocalDate;
    public static final DataType<Instant> Instant;
    public static final DataType<String> String;
    public static final DataType<byte[]> ByteArray;
    public static final DataType<UUID> UUID;

    static {
        map = new ArrayList<>();
        Integer = register(new DataTypeBase<>(int.class, Integer.class));
        Boolean = register(new DataTypeBase<>(boolean.class, Boolean.class));
        Byte = register(new DataTypeBase<>(byte.class, Byte.class));
        Short = register(new DataTypeBase<>(short.class, Short.class));
        Long = register(new DataTypeBase<>(long.class, Long.class));
        Decimal = register(new DataTypeBase<>(null, BigDecimal.class));
        Double = register(new DataTypeBase<>(double.class, Double.class));
        Float = register(new DataTypeBase<>(float.class, Float.class));
        LocalTime = register(new DataTypeBase<>(null, java.time.LocalTime.class));
        LocalDate = register(new DataTypeBase<>(null, java.time.LocalDate.class));
        Instant = register(new DataTypeBase<>(null, java.time.Instant.class));
        Other = register(new DataTypeBase<>(null, null));
        String = register(new DataTypeBase<>(null, String.class));
        ByteArray = register(new DataTypeBase<>(null, byte[].class));
        UUID = register(new DataTypeBase<>(null, java.util.UUID.class));
    }

    public static DataType<?>[] values() {
        return map.toArray(new DataType[map.size()]);
    }

    private static <T> DataType<T> register(final DataType<T> dataTypeBase) {
        map.add(dataTypeBase);
        return dataTypeBase;
    }
}
