package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

public interface DataFilter<T> {

    boolean handleRaw(Object dataObject, Object fieldParser);
}
