package com.base2art.eventSourcedDataAccess.filtering;

import java.util.Optional;

public interface SimpleEquatableField<T> {

    void in(T[] values);

    Optional<T[]> in();

    void equalTo(T value);

    Optional<T> equalTo();

    void notEqualTo(T value);

    Optional<T> notEqualTo();

    default void inSet(T... values) {

        this.in(values);
    }
}
