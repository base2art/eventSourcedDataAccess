package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.DateTimeField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultDateTimeField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.time.Instant;

public class InstantDataFilter extends DataFilterBase<Instant, DateTimeField, DefaultDateTimeField> {
//    public InstantDataFilter() {
//        super(null, Instant.class, DateTimeField.class, DefaultDateTimeField.class);
//    }

    @Override
    protected boolean handle(final Instant type, final DateTimeField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
