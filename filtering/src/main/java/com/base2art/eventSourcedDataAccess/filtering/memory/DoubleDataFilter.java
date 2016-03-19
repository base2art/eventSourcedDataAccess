package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.DoubleNumberFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class DoubleDataFilter extends DataFilterBase<Double, DoubleNumberFilterField> {

    @Override
    public boolean handle(final Double type, final DoubleNumberFilterField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
