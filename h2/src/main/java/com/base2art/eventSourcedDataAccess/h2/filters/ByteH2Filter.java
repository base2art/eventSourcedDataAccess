package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.ByteNumberFilterField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

public class ByteH2Filter extends H2FilterBase<Byte, ByteNumberFilterField> {

    @Override
    public void handle(final String columnName, final ByteNumberFilterField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqualAndCompare(columnName, filterField, collection, H2TypeRegistrar.Byte);
    }
}
