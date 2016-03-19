package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableField;

public interface H2Filter<Type, Field extends SimpleEquatableField<Type>> extends RawH2Filter {

    void handle(final String columnName, final Field filterField, H2ClauseCollection collection);
}
