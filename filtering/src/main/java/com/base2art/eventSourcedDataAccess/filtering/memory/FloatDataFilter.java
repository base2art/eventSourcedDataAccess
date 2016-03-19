package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.FloatNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class FloatDataFilter extends DataFilterBase<Float, FloatNumberField> {

    @Override
    public boolean handle(final Float type, final FloatNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
