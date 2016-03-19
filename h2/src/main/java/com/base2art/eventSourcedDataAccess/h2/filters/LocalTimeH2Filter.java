package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.TimeFilterField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

import java.time.LocalTime;

public class LocalTimeH2Filter extends H2FilterBase<LocalTime, TimeFilterField> {

    @Override
    public void handle(final String columnName, final TimeFilterField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqualAndCompare(columnName, filterField, collection, H2TypeRegistrar.LocalTime);
    }
}
