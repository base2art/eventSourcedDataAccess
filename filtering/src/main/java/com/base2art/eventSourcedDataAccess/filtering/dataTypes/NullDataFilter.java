package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableField;

public class NullDataFilter<T, U extends SimpleEquatableField<T>, V extends U>
        extends DataFilterBase<T, U, V> {

//    public NullDataFilter() {
//        super(null, null, null, null);
//    }

    @Override
    protected boolean handle(final T type, final U filterField) {
        return true;
    }
}
