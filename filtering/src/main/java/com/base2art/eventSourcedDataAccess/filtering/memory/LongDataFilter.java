package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.LongNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class LongDataFilter extends DataFilterBase<Long, LongNumberField> {

    @Override
    public boolean handle(final Long type, final LongNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
