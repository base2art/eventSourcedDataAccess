package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.h2.parameters.*;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class H2TypeRegistrar {

    public static final List<H2Type> params;

    private static final H2Type Other;

    static {

        params = new ArrayList<>();
        register(new IntH2Type());
        register(new BooleanH2Type());
        register(new ByteH2Type());
        register(new ShortH2Type());
        register(new LongH2Type());
        register(new DecimalH2Type());
        register(new DoubleH2Type());
        register(new FloatH2Type());
        register(new TimeH2Type());
        register(new DateH2Type());
        register(new DateTimeH2Type());
        Other = register(new ObjectH2Type());

        register(new StringH2Type());
        register(new ByteArrayH2Type());
        register(new UUIDH2Type());
    }

    public static H2Type register(final H2Type parameter) {
        params.add(parameter);
        return parameter;
    }

    //    Identity("IDENTITY", long.class, Long.class, (stmt, pos, val) -> stmt.setBoolean(pos, (boolean)val)),
    //    BINARY("BINARY", null, byte[].class, (stmt, pos, val) -> stmt.setBoolean(pos, (boolean)val)),
    //    Document("VARCHAR_IGNORECASE(MAX)", null, java.util.Date.class),

    public static H2Type getTypeByField(final Class<?> x) {
        for (H2Type type : params) {
            Class<?> primitiveType = type.getPrimitiveClass();
            Class<?> nonPrimitiveType = type.getNonPrimitiveClass();
            if (primitiveType != null && primitiveType == x) {
                return type;
            }

            if (nonPrimitiveType != null && nonPrimitiveType == x) {
                return type;
            }
        }

        return Other;
    }
}
