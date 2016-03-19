package com.base2art.eventSourcedDataAccess.filtering.utils;

import com.base2art.eventSourcedDataAccess.utils.Reflection;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class FilterReader {

    public static <FilterOption> Map<String, Object> getFilters(FilterOption filterOptions) {

        Map<String, Object> map = new HashMap<>();

        Reflection.fields(filterOptions.getClass())
                  .forEach(x -> {
                      x.setAccessible(true);
                      try {
                          map.put(x.getName(), x.get(filterOptions));
                      }
                      catch (IllegalAccessException e) {
                          throw new RuntimeException(e);
                      }
                  });

        return map;
    }
}
