package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.IntNumberFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class IntDataFilter extends DataFilterBase<Integer, IntNumberFilterField> {

    @Override
    public boolean handle(final Integer type, final IntNumberFilterField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
