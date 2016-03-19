package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableField;

public abstract class DataFilterBase<T, U extends SimpleEquatableField<T>>
        implements DataFilter<T, U> {

    @SuppressWarnings("unchecked")
    public boolean handleRaw(final Object type, final Object filterField) {
        return this.handle((T) type, (U) filterField);
    }

    public abstract boolean handle(final T type, final U filterField);
}
