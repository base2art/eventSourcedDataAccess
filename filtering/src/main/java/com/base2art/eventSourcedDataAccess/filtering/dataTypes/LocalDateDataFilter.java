package com.base2art.eventSourcedDataAccess.filtering.dataTypes;

import com.base2art.eventSourcedDataAccess.filtering.DateField;
import com.base2art.eventSourcedDataAccess.filtering.impls.DefaultDateField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.time.LocalDate;

public class LocalDateDataFilter extends DataFilterBase<LocalDate, DateField, DefaultDateField> {
//    public LocalDateDataFilter() {
//        super(null, LocalDate.class, DateField.class, DefaultDateField.class);
//    }

    @Override
    protected boolean handle(final LocalDate type, final DateField filterField) {

        if (!CompareUtils.handleEqual(type, filterField)) {
            return false;
        }

        return CompareUtils.handleComparable(type, filterField, LocalDate::compareTo);
    }
}
