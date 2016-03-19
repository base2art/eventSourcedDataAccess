package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.DecimalNumberFilterField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.math.BigDecimal;

public class DecimalDataFilter extends DataFilterBase<BigDecimal, DecimalNumberFilterField> {

    @Override
    public boolean handle(final BigDecimal type, final DecimalNumberFilterField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
