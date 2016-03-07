package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.ByteNumberField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultByteNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class ByteDataFilter extends DataFilterBase<Byte, ByteNumberField, DefaultByteNumberField> {
//    public ByteDataFilter() {
//        super(byte.class, Byte.class, ByteNumberField.class, DefaultByteNumberField.class);
//    }

    @Override
    protected boolean handle(final Byte type, final ByteNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
