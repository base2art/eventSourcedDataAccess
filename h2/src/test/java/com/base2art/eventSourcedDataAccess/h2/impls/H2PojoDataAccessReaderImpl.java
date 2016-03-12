package com.base2art.eventSourcedDataAccess.h2.impls;

import com.base2art.eventSourcedDataAccess.h2.H2Connector;
import com.base2art.eventSourcedDataAccess.h2.H2Filterer;
import com.base2art.eventSourcedDataAccess.h2.H2Orderer;
import com.base2art.eventSourcedDataAccess.h2.H2PojoDataAccessReader;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;

import java.util.UUID;

public class H2PojoDataAccessReaderImpl
        extends H2PojoDataAccessReader<UUID, Person, PersonData, PersonVersionData, PersonFilterOptions, PersonOrderOptions> {

    public H2PojoDataAccessReaderImpl(H2Connector<UUID> connector) {
        super(
                Person.class,
                H2PojoDataAccessReaderImpl::map,
                connector,
                new H2Filterer<Person, PersonFilterOptions>() {},
                new H2Orderer<Person, PersonOrderOptions>() {});
    }

    private static Person map(final UUID uuid, final PersonData personData, final PersonVersionData personVersionData) {
        return new Person(uuid, personData, personVersionData);
    }

    @Override
    public PersonVersionData createVersionObjectData(final UUID uuid) {
        return new PersonVersionData();
    }

    @Override
    public PersonData createObjectData(final UUID uuid) {

        PersonData person = new PersonData();
        return person;
    }
}
