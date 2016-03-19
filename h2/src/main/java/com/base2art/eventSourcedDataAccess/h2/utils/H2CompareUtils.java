package com.base2art.eventSourcedDataAccess.h2.utils;

import com.base2art.eventSourcedDataAccess.filtering.SimpleComparableFilterField;
import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableFilterField;
import com.base2art.eventSourcedDataAccess.h2.filters.H2ClauseCollection;
import com.base2art.eventSourcedDataAccess.h2.parameters.H2Type;
import lombok.experimental.UtilityClass;

@UtilityClass
public class H2CompareUtils {

    public static <T, U extends SimpleComparableFilterField<T>> void handleComparable(
            final String columnName,
            final U filterField,
            final H2ClauseCollection collection,
            final H2Type<T> setup) {

        if (filterField.greaterThanOrEqualTo().isPresent()) {
            collection.add(1, columnName + " >= ?", (s, pos) -> setup.setValue(s, pos, filterField.greaterThanOrEqualTo().get()));
        }

        if (filterField.lessThanOrEqualTo().isPresent()) {
            collection.add(1, columnName + " <= ?", (s, pos) -> setup.setValue(s, pos, filterField.lessThanOrEqualTo().get()));
        }
    }

    public static <T, U extends SimpleEquatableFilterField<T>> void handleEqual(
            final String columnName,
            final U filterField,
            final H2ClauseCollection collection,
            final H2Type<T> setup) {

        if (filterField.equalTo().isPresent()) {
            collection.add(1, columnName + " = ?", (s, pos) -> setup.setValue(s, pos, filterField.equalTo().get()));
        }

        if (filterField.notEqualTo().isPresent()) {
            collection.add(1, columnName + " <> ?", (s, pos) -> setup.setValue(s, pos, filterField.notEqualTo().get()));
        }

//        if (filterField.in().isPresent()) {
//            if (!Arrays.stream(filterField.in().get())
//                       .filter(x -> x.equals(type))
//                       .findAny().isPresent()) {
//                return false;
//            }
//        }

    }

    public static <T, U extends SimpleComparableFilterField<T>> void handleEqualAndCompare(
            final String columnName,
            final U filterField,
            final H2ClauseCollection collection,
            final H2Type<T> setup) {
        handleComparable(columnName, filterField, collection, setup);
        handleEqual(columnName, filterField, collection, setup);
    }
}

