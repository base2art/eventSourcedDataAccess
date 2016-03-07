package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.BooleanField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultBooleanField;

public class BooleanDataFilter extends DataFilterBase<Boolean, BooleanField, DefaultBooleanField> {
//    public BooleanDataFilter() {
//        super(boolean.class, Boolean.class, BooleanField.class, DefaultBooleanField.class);
//    }

    @Override
    protected boolean handle(final Boolean type, final BooleanField filterField) {

        if (filterField.equalTo().isPresent()) {
            if (!filterField.equalTo().get().equals(type)) {
                return false;
            }
        }

        return true;
    }
}
