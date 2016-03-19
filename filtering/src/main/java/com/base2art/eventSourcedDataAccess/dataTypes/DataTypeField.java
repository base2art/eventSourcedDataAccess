package com.base2art.eventSourcedDataAccess.dataTypes;

public interface DataTypeField<T> {

    DataType<T> getDataType();

    Class<? extends FilterField<T>> getFieldInterface();

    Class<? extends FilterField<T>> getFieldClass();
}
