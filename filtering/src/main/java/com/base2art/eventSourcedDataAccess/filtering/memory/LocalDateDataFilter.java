package com.base2art.eventSourcedDataAccess.filtering.memory;

import com.base2art.eventSourcedDataAccess.filtering.DateField;
import com.base2art.eventSourcedDataAccess.filtering.utils.CompareUtils;

import java.time.LocalDate;

public class LocalDateDataFilter extends DataFilterBase<LocalDate, DateField> {

    @Override
    public boolean handle(final LocalDate type, final DateField filterField) {

        if (!CompareUtils.handleEqual(type, filterField)) {
            return false;
        }

        return CompareUtils.handleComparable(type, filterField, LocalDate::compareTo);
    }
}
