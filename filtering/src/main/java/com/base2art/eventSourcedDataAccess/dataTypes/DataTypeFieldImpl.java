package com.base2art.eventSourcedDataAccess.dataTypes;

import lombok.Data;

@Data
public class DataTypeFieldImpl<T, U extends FilterField<T>> implements DataTypeField<T> {

    private final DataType<T> dataType;
    private final Class<U> fieldInterface;
    private final Class<? extends U> fieldClass;
}
