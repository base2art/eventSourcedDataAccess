package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.ShortNumberField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultShortNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class ShortDataFilter extends DataFilterBase<Short, ShortNumberField, DefaultShortNumberField> {
//    public ShortDataFilter() {
//        super(short.class, Short.class, ShortNumberField.class, DefaultShortNumberField.class);
//    }

    @Override
    protected boolean handle(final Short type, final ShortNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
