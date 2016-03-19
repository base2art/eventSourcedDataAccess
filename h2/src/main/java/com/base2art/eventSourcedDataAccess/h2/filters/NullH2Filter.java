package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableFilterField;

public class NullH2Filter<T, U extends SimpleEquatableFilterField<T>>
        extends H2FilterBase<T, U> {

    @Override
    public void handle(final String columnName, final U filterField, final H2ClauseCollection collection) {
    }
}
