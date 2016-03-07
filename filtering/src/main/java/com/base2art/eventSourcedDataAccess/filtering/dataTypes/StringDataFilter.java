package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.TextField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultTextField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

public class StringDataFilter extends DataFilterBase<String, TextField, DefaultTextField> {
//    public StringDataFilter() {
//        super(null, String.class, TextField.class, DefaultTextField.class);
//    }

    @Override
    protected boolean handle(final String type, final TextField filterField) {

        if (!CompareUtils.handleEqual(type, filterField)) {
            return false;
        }

        if (!CompareUtils.handleComparable(type, filterField)) {
            return false;
        }

        // DO ThE REST;

        if (filterField.contains().isPresent()) {
            if (type == null || !type.contains(filterField.contains().get())) {
                return false;
            }
        }

        if (filterField.matches().isPresent()) {
            if (type == null || !type.matches(filterField.matches().get().pattern())) {
                return false;
            }
        }

        if (filterField.endsWith().isPresent()) {
            if (type == null || !type.endsWith(filterField.endsWith().get())) {
                return false;
            }
        }

        if (filterField.equalToIgnoreCase().isPresent()) {
            if (type == null || !type.equalsIgnoreCase(filterField.equalToIgnoreCase().get())) {
                return false;
            }
        }

        if (filterField.isNotNullOrEmpty().isPresent()) {
            if (type == null || type.isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
