package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.ByteNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class ByteDataFilter extends DataFilterBase<Byte, ByteNumberField> {

    @Override
    public boolean handle(final Byte type, final ByteNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
