package com.base2art.eventSourcedDataAccess.testing.pojo.fixtures;


import java.util.UUID;

public class Person {

    private final UUID uuid;

    private final PersonData personData;

    private final PersonVersionData personVersionData;

    public Person(final UUID uuid, final PersonData personData, final PersonVersionData personVersionData) {

        this.uuid = uuid;
        this.personData = personData;
        this.personVersionData = personVersionData;
    }

    public String getSocialSecurityNumber() {
        return this.personData.getSocialSecurityNumber();
    }

    public String getName() {
        return this.personVersionData.getName();
    }

    public UUID getId() {
        return uuid;
    }
}