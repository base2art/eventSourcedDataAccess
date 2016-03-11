package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.BooleanField;

public class BooleanDataFilter extends DataFilterBase<Boolean, BooleanField> {

    @Override
    public boolean handle(final Boolean type, final BooleanField filterField) {

        if (filterField.equalTo().isPresent()) {
            if (!filterField.equalTo().get().equals(type)) {
                return false;
            }
        }

        return true;
    }
}
