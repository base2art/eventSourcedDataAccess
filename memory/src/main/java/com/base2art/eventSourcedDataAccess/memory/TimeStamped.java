package com.base2art.eventSourcedDataAccess.memory;

import lombok.Getter;

import java.time.Instant;

public class TimeStamped<T> {

    @Getter
    private final Instant time = Instant.now();

    @Getter
    private final T data;

    public TimeStamped(T data) {
        this.data = data;
    }
}
