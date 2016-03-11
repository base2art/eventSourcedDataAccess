package com.base2art.eventSourcedDataAccess.filtering.memory;

public interface RawDataFilter {

    boolean handleRaw(Object dataObject, Object fieldParser);
}
