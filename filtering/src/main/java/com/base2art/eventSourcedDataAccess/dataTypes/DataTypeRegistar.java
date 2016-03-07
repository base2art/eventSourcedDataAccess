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
        Integer = register(new DataTypeImpl<>(int.class, Integer.class));
        Boolean = register(new DataTypeImpl<>(boolean.class, Boolean.class));
        Byte = register(new DataTypeImpl<>(byte.class, Byte.class));
        Short = register(new DataTypeImpl<>(short.class, Short.class));
        Long = register(new DataTypeImpl<>(long.class, Long.class));
        Decimal = register(new DataTypeImpl<>(null, BigDecimal.class));
        Double = register(new DataTypeImpl<>(double.class, Double.class));
        Float = register(new DataTypeImpl<>(float.class, Float.class));
        LocalTime = register(new DataTypeImpl<>(null, java.time.LocalTime.class));
        LocalDate = register(new DataTypeImpl<>(null, java.time.LocalDate.class));
        Instant = register(new DataTypeImpl<>(null, java.time.Instant.class));
        Other = register(new DataTypeImpl<>(null, null));
        String = register(new DataTypeImpl<>(null, String.class));
        ByteArray = register(new DataTypeImpl<>(null, byte[].class));
        UUID = register(new DataTypeImpl<>(null, java.util.UUID.class));
    }

    public static DataType<?>[] values() {
        return map.toArray(new DataType[map.size()]);
    }

    private static <T> DataType<T> register(final DataType<T> dataTypeBase) {
        map.add(dataTypeBase);
        return dataTypeBase;
    }
}
