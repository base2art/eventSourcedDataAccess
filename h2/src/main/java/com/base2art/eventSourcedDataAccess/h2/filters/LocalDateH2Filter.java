package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.DateFilterField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

import java.time.LocalDate;

public class LocalDateH2Filter extends H2FilterBase<LocalDate, DateFilterField> {

    @Override
    public void handle(final String columnName, final DateFilterField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqualAndCompare(columnName, filterField, collection, H2TypeRegistrar.LocalDate);
    }
}
