package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.ShortNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class ShortDataFilter extends DataFilterBase<Short, ShortNumberField> {

    @Override
    public boolean handle(final Short type, final ShortNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
