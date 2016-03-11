package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.DecimalNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.math.BigDecimal;

public class DecimalDataFilter extends DataFilterBase<BigDecimal, DecimalNumberField> {

    @Override
    public boolean handle(final BigDecimal type, final DecimalNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
