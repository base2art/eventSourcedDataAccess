package com.base2art.eventSourcedDataAccess.memory;

import lombok.Getter;

import java.time.ZonedDateTime;


public class TimeStamped<T> {

    @Getter
    private final ZonedDateTime time = ZonedDateTime.now();

    @Getter
    private final T data;

    public TimeStamped(T data) {
        this.data = data;
    }
}
