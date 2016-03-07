package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.FloatNumberField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultFloatNumberField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class FloatDataFilter extends DataFilterBase<Float, FloatNumberField, DefaultFloatNumberField> {
//    public FloatDataFilter() {
//        super(float.class, Float.class, FloatNumberField.class, DefaultFloatNumberField.class);
//    }

    @Override
    protected boolean handle(final Float type, final FloatNumberField filterField) {
        return CompareUtils.handleEqual(type, filterField) && CompareUtils.handleComparable(type, filterField);
    }
}
