package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableFilterField;

public interface H2Filter<Type, Field extends SimpleEquatableFilterField<Type>> extends RawH2Filter {

    void handle(final String columnName, final Field filterField, H2ClauseCollection collection);
}
