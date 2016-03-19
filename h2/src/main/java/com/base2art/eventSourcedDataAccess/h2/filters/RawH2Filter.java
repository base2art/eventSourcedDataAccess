package com.base2art.eventSourcedDataAccess.h2.filters;

public interface RawH2Filter {

    void handleRaw(String columnName, Object fieldParser, H2ClauseCollection collection);
}
