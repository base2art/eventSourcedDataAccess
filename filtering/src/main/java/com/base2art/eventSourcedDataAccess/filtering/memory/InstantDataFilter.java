package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.DateTimeFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.time.Instant;

public class InstantDataFilter extends DataFilterBase<Instant, DateTimeFilterField> {

    @Override
    public boolean handle(final Instant type, final DateTimeFilterField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
