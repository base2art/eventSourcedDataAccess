package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.TimeFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.time.LocalTime;

public class LocalTimeDataFilter extends DataFilterBase<LocalTime, TimeFilterField> {

    @Override
    public boolean handle(final LocalTime type, final TimeFilterField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
