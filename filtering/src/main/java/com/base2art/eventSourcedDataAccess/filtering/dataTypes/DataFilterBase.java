package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableField;

public abstract class DataFilterBase<T, U extends SimpleEquatableField<T>, V extends U>
        implements DataFilter<T> {

    public DataFilterBase() {
    }

    @SuppressWarnings("unchecked")
    public boolean handleRaw(final Object type, final Object filterField) {
        return this.handle((T) type, (U) filterField);
    }

    protected abstract boolean handle(final T type, final U filterField);
}
