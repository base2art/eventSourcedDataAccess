package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.DoubleNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class DoubleDataFilter extends DataFilterBase<Double, DoubleNumberField> {

    @Override
    public boolean handle(final Double type, final DoubleNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
