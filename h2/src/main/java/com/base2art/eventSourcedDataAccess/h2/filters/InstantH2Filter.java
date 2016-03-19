package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.DateTimeFilterField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

import java.time.Instant;

public class InstantH2Filter extends H2FilterBase<Instant, DateTimeFilterField> {

    @Override
    public void handle(final String columnName, final DateTimeFilterField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqualAndCompare(columnName, filterField, collection, H2TypeRegistrar.Instant);
    }
}
