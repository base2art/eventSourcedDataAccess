package com.base2art.eventSourcedDataAccess;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class EnumSortParser {

    public static <T extends Enum<T>> SortInformation parse(T orderOptions) {


        String fieldName = orderOptions.toString();

        boolean asc = true;
        Map<String, Boolean> items = new HashMap<>();

        items.put("ASC", true);
        items.put("ASCENDING", true);
        items.put("DESCENDING", false);
        items.put("DESC", false);

        for (Map.Entry<String, Boolean> entry : items.entrySet()) {

            if (fieldName.toUpperCase().endsWith(entry.getKey())) {
                asc = true;
                fieldName = fieldName.substring(0, fieldName.length() - entry.getKey().length());
                break;
            }
        }

        final String newFieldName = fieldName;

        return new SortInformation(newFieldName, asc);
    }
}
