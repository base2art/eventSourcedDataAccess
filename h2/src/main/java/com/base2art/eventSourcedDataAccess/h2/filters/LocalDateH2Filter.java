package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.DateField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

import java.time.LocalDate;

public class LocalDateH2Filter extends H2FilterBase<LocalDate, DateField> {

    @Override
    public void handle(final String columnName, final DateField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqualAndCompare(columnName, filterField, collection, H2TypeRegistrar.LocalDate);
    }
}
