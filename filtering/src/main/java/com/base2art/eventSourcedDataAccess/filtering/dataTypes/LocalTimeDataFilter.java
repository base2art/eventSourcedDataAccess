package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.TimeField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultTimeField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.time.LocalTime;

public class LocalTimeDataFilter extends DataFilterBase<LocalTime, TimeField, DefaultTimeField> {
//    public LocalTimeDataFilter() {
//        super(null, LocalTime.class, TimeField.class, DefaultTimeField.class);
//    }

    @Override
    protected boolean handle(final LocalTime type, final TimeField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
