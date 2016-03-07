package com.base2art.eventSourcedDataAccess.filtering.impls;

import com.base2art.eventSourcedDataAccess.filtering.ComparableField;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;

@Accessors(fluent = true, chain = false)
public class DefaultComparableField<T extends Comparable<T>>
        extends DefaultEquatableField<T>
        implements ComparableField<T> {

    @Setter
    private T greaterThanOrEqualTo;

    @Setter
    private T lessThanOrEqualTo;

    @Override
    public Optional<T> greaterThanOrEqualTo() {
        return Optional.ofNullable(this.greaterThanOrEqualTo).map(this::clean);
    }

    @Override
    public Optional<T> lessThanOrEqualTo() {
        return Optional.ofNullable(this.lessThanOrEqualTo).map(this::clean);
    }
}
