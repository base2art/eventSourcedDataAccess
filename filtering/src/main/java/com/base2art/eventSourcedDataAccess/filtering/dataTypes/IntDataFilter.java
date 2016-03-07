package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.IntNumberField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultIntNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class IntDataFilter extends DataFilterBase<Integer, IntNumberField, DefaultIntNumberField> {
//    public IntDataFilter() {
//        super(int.class, Integer.class, IntNumberField.class, DefaultIntNumberField.class);
//    }

    @Override
    protected boolean handle(final Integer type, final IntNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
