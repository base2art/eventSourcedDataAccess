package com.base2art.eventSourcedDataAccess.filtering.fields;

import com.base2art.eventSourcedDataAccess.filtering.ComparableFilterField;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;

@Accessors(fluent = true, chain = false)
public class DefaultComparableFilterField<T extends Comparable<T>>
        extends DefaultEquatableFilterField<T>
        implements ComparableFilterField<T> {

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
