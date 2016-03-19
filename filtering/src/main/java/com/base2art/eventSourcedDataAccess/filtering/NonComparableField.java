package com.base2art.eventSourcedDataAccess.filtering;

import java.util.Optional;

public interface NonComparableField<T extends Comparable<T>>
        extends SimpleEquatableField<T> {

    void greaterThanOrEqualTo(T value);

    Optional<T> greaterThanOrEqualTo();

    void lessThanOrEqualTo(T value);

    Optional<T> lessThanOrEqualTo();
}
