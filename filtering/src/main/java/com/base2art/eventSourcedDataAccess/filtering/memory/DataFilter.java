package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableField;

public interface DataFilter<Type, Field extends SimpleEquatableField<Type>> extends RawDataFilter {

    boolean handle(final Type type, final Field filterField);
}
