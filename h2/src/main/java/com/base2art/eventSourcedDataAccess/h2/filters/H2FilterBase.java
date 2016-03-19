package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableField;

public abstract class H2FilterBase<T, U extends SimpleEquatableField<T>>
        implements H2Filter<T, U> {

    @SuppressWarnings("unchecked")
    public void handleRaw(final String name, final Object filterField, final H2ClauseCollection collection) {
        this.handle(name, (U) filterField, collection);
    }

    public abstract void handle(final String name, final U filterField, final H2ClauseCollection collection);
}
