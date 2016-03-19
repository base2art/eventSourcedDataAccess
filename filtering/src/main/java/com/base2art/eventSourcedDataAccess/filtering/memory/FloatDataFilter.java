package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.FloatNumberFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class FloatDataFilter extends DataFilterBase<Float, FloatNumberFilterField> {

    @Override
    public boolean handle(final Float type, final FloatNumberFilterField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
