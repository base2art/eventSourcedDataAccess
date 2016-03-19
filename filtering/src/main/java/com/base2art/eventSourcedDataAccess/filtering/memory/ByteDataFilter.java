package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.ByteNumberFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class ByteDataFilter extends DataFilterBase<Byte, ByteNumberFilterField> {

    @Override
    public boolean handle(final Byte type, final ByteNumberFilterField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
