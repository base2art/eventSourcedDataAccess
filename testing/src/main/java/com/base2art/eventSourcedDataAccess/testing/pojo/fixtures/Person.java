package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;

import lombok.Getter;

import java.util.UUID;

public class Person {

    @Getter
    private final UUID id;

    @Getter
    private final String socialSecurityNumber;

    @Getter
    private final String name;

    public Person(final UUID uuid, final PersonData personData, final PersonVersionData personVersionData) {

        this.id = uuid;
        this.socialSecurityNumber = personData.getSocialSecurityNumber();
        this.name = personVersionData.getName();
    }
}