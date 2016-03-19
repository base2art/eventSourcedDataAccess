package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableFilterField;

public interface DataFilter<Type, Field extends SimpleEquatableFilterField<Type>> extends RawDataFilter {

    boolean handle(final Type type, final Field filterField);
}
