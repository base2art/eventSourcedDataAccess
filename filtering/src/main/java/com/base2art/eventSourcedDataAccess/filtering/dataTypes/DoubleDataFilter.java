package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.DoubleNumberField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultDoubleNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class DoubleDataFilter extends DataFilterBase<Double, DoubleNumberField, DefaultDoubleNumberField> {
//    public DoubleDataFilter() {
//        super(double.class, Double.class, DoubleNumberField.class, DefaultDoubleNumberField.class);
//    }

    @Override
    protected boolean handle(final Double type, final DoubleNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
