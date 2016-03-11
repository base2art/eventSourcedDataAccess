package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.IntNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class IntDataFilter extends DataFilterBase<Integer, IntNumberField> {

    @Override
    public boolean handle(final Integer type, final IntNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
