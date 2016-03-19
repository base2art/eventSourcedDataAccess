package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.BooleanFilterField;

public class BooleanDataFilter extends DataFilterBase<Boolean, BooleanFilterField> {

    @Override
    public boolean handle(final Boolean type, final BooleanFilterField filterField) {

        if (filterField.equalTo().isPresent()) {
            if (!filterField.equalTo().get().equals(type)) {
                return false;
            }
        }

        return true;
    }
}
