package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.dataTypes.DataType;
import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeRegistrar;
import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeRegistrarQueries;
import com.base2art.eventSourcedDataAccess.h2.parameters.*;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class H2TypeRegistrar {

    private static final Map<DataType<?>, H2Type<?>> typeMap;

    public static final H2Type<Object> Other;
    public static final H2Type<Integer> Integer;
    public static final H2Type<Boolean> Boolean;
    public static final H2Type<Byte> Byte;
    public static final H2Type<Short> Short;
    public static final H2Type<Long> Long;
    public static final H2Type<BigDecimal> Decimal;
    public static final H2Type<Double> Double;
    public static final H2Type<Float> Float;
    public static final H2Type<LocalTime> LocalTime;
    public static final H2Type<LocalDate> LocalDate;
    public static final H2Type<Instant> Instant;
    public static final H2Type<String> String;
    public static final H2Type<byte[]> ByteArray;
    public static final H2Type<UUID> UUID;

    static {
        typeMap = new HashMap<>();
        Integer = register(DataTypeRegistrar.Integer, new IntH2Type());
        Boolean = register(DataTypeRegistrar.Boolean, new BooleanH2Type());
        Byte = register(DataTypeRegistrar.Byte, new ByteH2Type());
        Short = register(DataTypeRegistrar.Short, new ShortH2Type());
        Long = register(DataTypeRegistrar.Long, new LongH2Type());
        Decimal = register(DataTypeRegistrar.Decimal, new DecimalH2Type());
        Double = register(DataTypeRegistrar.Double, new DoubleH2Type());
        Float = register(DataTypeRegistrar.Float, new FloatH2Type());
        LocalTime = register(DataTypeRegistrar.LocalTime, new TimeH2Type());
        LocalDate = register(DataTypeRegistrar.LocalDate, new DateH2Type());
        Instant = register(DataTypeRegistrar.Instant, new DateTimeH2Type());
        Other = register(DataTypeRegistrar.Other, new ObjectH2Type());
        String = register(DataTypeRegistrar.String, new StringH2Type());
        ByteArray = register(DataTypeRegistrar.ByteArray, new ByteArrayH2Type());
        UUID = register(DataTypeRegistrar.UUID, new UUIDH2Type());
    }

    public static <T> H2Type<T> register(final DataType<T> dataType, final H2Type<T> parameter) {
        typeMap.put(dataType, parameter);
        return parameter;
    }

    public static RawH2Type getTypeByField(final Class<?> type) {
        return DataTypeRegistrarQueries.getByClass(typeMap, type, () -> Other);
    }

    //    Identity("IDENTITY", long.class, Long.class, (stmt, pos, val) -> stmt.setBoolean(pos, (boolean)val)),
    //    BINARY("BINARY", null, byte[].class, (stmt, pos, val) -> stmt.setBoolean(pos, (boolean)val)),
    //    Document("VARCHAR_IGNORECASE(MAX)", null, java.util.Date.class),
}

