package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.TimeField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.time.LocalTime;

public class LocalTimeDataFilter extends DataFilterBase<LocalTime, TimeField> {

    @Override
    public boolean handle(final LocalTime type, final TimeField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
