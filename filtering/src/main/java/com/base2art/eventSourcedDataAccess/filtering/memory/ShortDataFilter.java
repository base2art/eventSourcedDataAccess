package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.ShortNumberFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class ShortDataFilter extends DataFilterBase<Short, ShortNumberFilterField> {

    @Override
    public boolean handle(final Short type, final ShortNumberFilterField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
