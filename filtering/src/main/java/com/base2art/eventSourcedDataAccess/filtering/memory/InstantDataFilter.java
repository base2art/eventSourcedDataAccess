package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.DateTimeField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.time.Instant;

public class InstantDataFilter extends DataFilterBase<Instant, DateTimeField> {

    @Override
    public boolean handle(final Instant type, final DateTimeField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
