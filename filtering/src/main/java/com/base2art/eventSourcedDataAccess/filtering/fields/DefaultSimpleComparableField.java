package com.base2art.eventSourcedDataAccess.filtering.fields;

import com.base2art.eventSourcedDataAccess.filtering.SimpleComparableField;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Optional;

@Accessors(fluent = true, chain = false)
public class DefaultSimpleComparableField<T>
        extends DefaultSimpleEquatableField<T>
        implements SimpleComparableField<T> {

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
