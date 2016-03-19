package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableField;

public class NullDataFilter<T, U extends SimpleEquatableField<T>>
        extends DataFilterBase<T, U> {


    @Override
    public boolean handle(final T type, final U filterField) {
        return true;
    }
}
