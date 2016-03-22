package com.base2art.eventSourcedDataAccess.git;

@FunctionalInterface
public interface Procedure {
    void call() throws Exception;
}
