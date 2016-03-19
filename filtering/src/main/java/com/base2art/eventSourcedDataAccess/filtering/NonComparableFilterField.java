package com.base2art.eventSourcedDataAccess.filtering;

import java.util.Optional;

public interface NonComparableFilterField<T extends Comparable<T>>
        extends SimpleEquatableFilterField<T> {

    void greaterThanOrEqualTo(T value);

    Optional<T> greaterThanOrEqualTo();

    void lessThanOrEqualTo(T value);

    Optional<T> lessThanOrEqualTo();
}
