package com.base2art.eventSourcedDataAccess.h2.filters;

import com.base2art.eventSourcedDataAccess.filtering.BooleanField;

public class BooleanH2Filter extends H2FilterBase<Boolean, BooleanField> {

    @Override
    public void handle(final String columnName, final BooleanField filterField, final H2ClauseCollection collection) {

        if (filterField.equalTo().isPresent()) {
            collection.add(1, columnName + " = ?", (s, pos) -> s.setBoolean(pos, filterField.equalTo().get()));
        }
    }
}
