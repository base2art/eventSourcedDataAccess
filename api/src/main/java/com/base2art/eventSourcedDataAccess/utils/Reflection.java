package com.base2art.eventSourcedDataAccess.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reflection {

    public static Field[] getAllFields(final Class<?> klazz) {

        return fields(klazz)
                .collect(Collectors.toList())
                .toArray(new Field[0]);
    }

    public static Stream<Field> fields(final Class<?> klazz) {

        if (klazz == null) {
            return Stream.empty();
        }

        return Stream.concat(
                Arrays.stream(klazz.getDeclaredFields()),
                fields(klazz.getSuperclass()));
    }

    public static Method[] getAllMethods(final Class<?> klazz) {

        return methods(klazz)
                .collect(Collectors.toList())
                .toArray(new Method[0]);
    }

    public static Stream<Method> methods(final Class<?> klazz) {

        if (klazz == null) {
            return Stream.empty();
        }

        return Stream.concat(
                Arrays.stream(klazz.getDeclaredMethods()),
                methods(klazz.getSuperclass()));
    }
}
