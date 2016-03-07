package com.base2art.eventSourcedDataAccess.utils;

import java.lang.reflect.Array;

public final class Arrays {

    private Arrays() {}

    public static <E> E[] createGenericArray(Class<E> elementType, int length) {

        // Use Array native method to create array
        // of a type only known at run time
        @SuppressWarnings("unchecked")
        final E[] a = (E[]) Array.newInstance(elementType, length);

        return a;
    }
}
