package com.base2art.eventSourcedDataAccess.dataTypes;

import lombok.Getter;

public class DataTypeImpl<T> implements DataType<T> {

    @Getter
    private final Class<T> primitiveClass;

    @Getter
    private final Class<T> nonPrimitiveClass;

    public DataTypeImpl(
            final Class<T> primitiveClass,
            final Class<T> nonPrimitiveClass) {

        this.primitiveClass = primitiveClass;
        this.nonPrimitiveClass = nonPrimitiveClass;
    }
}
