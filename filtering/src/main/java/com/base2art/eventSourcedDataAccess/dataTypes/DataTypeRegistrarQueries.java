package com.base2art.eventSourcedDataAccess.dataTypes;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Map;

@UtilityClass
public class DataTypeRegistrarQueries {
    public static <T> T getByClass(
            final Map<DataType<?>, T> map,
            final Class<?> dataType,
            final java.util.function.Supplier<T> defaultValue) {

        for (Map.Entry<DataType<?>, T> typeEntry : map.entrySet()) {

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

        if (defaultValue != null) {
            return defaultValue.get();
        }

        return null;
    }
}
