package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.DecimalNumberField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

import java.math.BigDecimal;

public class DecimalH2Filter extends H2FilterBase<BigDecimal, DecimalNumberField> {

    @Override
    public void handle(final String columnName, final DecimalNumberField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqualAndCompare(columnName, filterField, collection, H2TypeRegistrar.Decimal);
    }
}
