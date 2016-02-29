package com.base2art.eventSourcedDataAccess.testing.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ResultableService<T> {

    @Getter
    private final T service;

    private final boolean hasError;

    public boolean hasError() {
        return hasError;
    }
}
