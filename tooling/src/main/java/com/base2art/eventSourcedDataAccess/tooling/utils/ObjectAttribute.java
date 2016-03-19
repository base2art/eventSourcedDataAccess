package com.base2art.eventSourcedDataAccess.tooling.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ObjectAttribute<T> {

    private final boolean isObjectProperty;
    private final T attribute;
}
