package com.base2art.eventSourcedDataAccess;

import lombok.Data;

@Data
public class SortInformation {

    private final String fieldName;
    private final boolean ascending;
}
