package com.base2art.eventSourcedDataAccess.dataTypes;

import lombok.Getter;

public class DataTypeBase<T> implements DataType {

    @Getter
    private final Class<T> primitiveClass;

    @Getter
    private final Class<T> nonPrimitiveClass;

    public DataTypeBase(
            final Class<T> primitiveClass,
            final Class<T> nonPrimitiveClass) {

        this.primitiveClass = primitiveClass;
        this.nonPrimitiveClass = nonPrimitiveClass;
    }
}
