package com.base2art.eventSourcedDataAccess.dataTypes;

public interface DataType<T> {
    Class<T> getPrimitiveClass();

    Class<T> getNonPrimitiveClass();
}
