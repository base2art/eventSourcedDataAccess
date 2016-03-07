package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.LongNumberField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultLongNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class LongDataFilter extends DataFilterBase<Long, LongNumberField, DefaultLongNumberField> {
//    public LongDataFilter() {
//        super(long.class, Long.class, LongNumberField.class, DefaultLongNumberField.class);
//    }

    @Override
    protected boolean handle(final Long type, final LongNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
