package com.base2art.eventSourcedDataAccess.filtering.utils;

import com.base2art.eventSourcedDataAccess.filtering.SimpleComparableFilterField;
import com.base2art.eventSourcedDataAccess.filtering.SimpleEquatableFilterField;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Comparator;

@UtilityClass
public class CompareUtils {

    public static <T extends Comparable<T>, U extends SimpleComparableFilterField<T>> boolean handleComparable(final T type, final U filterField) {

        if (filterField.greaterThanOrEqualTo().isPresent()) {
            if (filterField.greaterThanOrEqualTo().get().compareTo(type) < 0) {
                return false;
            }
        }

        if (filterField.lessThanOrEqualTo().isPresent()) {
            if (filterField.lessThanOrEqualTo().get().compareTo(type) > 0) {
                return false;
            }
        }

        return true;
    }

    public static <T, U extends SimpleComparableFilterField<T>> boolean handleComparable(final T type, final U filterField, Comparator<T> comp) {

        if (filterField.greaterThanOrEqualTo().isPresent()) {
            if (comp.compare(filterField.greaterThanOrEqualTo().get(), type) < 0) {
                return false;
            }
        }

        if (filterField.lessThanOrEqualTo().isPresent()) {
            if (comp.compare(filterField.lessThanOrEqualTo().get(), type) > 0) {
                return false;
            }
        }

        return true;
    }

    public static <T, U extends SimpleEquatableFilterField<T>> boolean handleEqual(final T type, final U filterField) {

        if (filterField.equalTo().isPresent()) {
            if (!filterField.equalTo().get().equals(type)) {
                return false;
            }
        }

        if (filterField.notEqualTo().isPresent()) {
            if (filterField.notEqualTo().get().equals(type)) {
                return false;
            }
        }

        if (filterField.in().isPresent()) {
            if (!Arrays.stream(filterField.in().get())
                       .filter(x -> x.equals(type))
                       .findAny().isPresent()) {
                return false;
            }
        }

        return true;
    }
}
