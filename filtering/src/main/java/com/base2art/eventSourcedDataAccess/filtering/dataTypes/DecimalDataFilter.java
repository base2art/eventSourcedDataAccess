package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.DecimalNumberField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultDecimalNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.math.BigDecimal;

public class DecimalDataFilter extends DataFilterBase<BigDecimal, DecimalNumberField, DefaultDecimalNumberField> {
//    public DecimalDataFilter() {
//        super(null, BigDecimal.class, DecimalNumberField.class, DefaultDecimalNumberField.class);
//    }

    @Override
    protected boolean handle(final BigDecimal type, final DecimalNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
