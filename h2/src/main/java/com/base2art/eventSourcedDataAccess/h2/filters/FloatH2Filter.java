package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.FloatNumberFilterField;
import com.base2art.eventSourcedDataAccess.h2.H2TypeRegistrar;
import com.base2art.eventSourcedDataAccess.h2.utils.H2CompareUtils;

public class FloatH2Filter extends H2FilterBase<Float, FloatNumberFilterField> {

    @Override
    public void handle(final String columnName, final FloatNumberFilterField filterField, final H2ClauseCollection collection) {

        H2CompareUtils.handleEqualAndCompare(columnName, filterField, collection, H2TypeRegistrar.Float);
    }
}
