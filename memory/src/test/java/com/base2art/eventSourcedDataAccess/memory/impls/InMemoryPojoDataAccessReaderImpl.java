package com.base2art.eventSourcedDataAccess.memory.impls;

import com.base2art.eventSourcedDataAccess.memory.Archivable;
import com.base2art.eventSourcedDataAccess.memory.InMemoryPojoDataAccessReader;
import com.base2art.eventSourcedDataAccess.memory.TimeStamped;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterer;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderer;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import com.google.common.collect.Multimap;

import java.util.Map;
import java.util.UUID;

public class InMemoryPojoDataAccessReaderImpl
        extends InMemoryPojoDataAccessReader<UUID, Person, PersonData, PersonVersionData, PersonFilterOptions, PersonOrderOptions> {

    public InMemoryPojoDataAccessReaderImpl(
            final Map<UUID, Archivable<PersonData>> objectDatas,
            final Multimap<UUID, TimeStamped<PersonVersionData>> versionObjectDatas) {
        super(
                Person.class,
                InMemoryPojoDataAccessReaderImpl::map,
                objectDatas,
                versionObjectDatas,
                new PersonFilterer(),
                new PersonOrderer());
    }

    private static Person map(final UUID uuid, final PersonData personData, final PersonVersionData personVersionData) {
        return new Person(uuid, personData, personVersionData);
    }


    @Override
    protected UUID getIdForEntity(final Person x) {
        return x.getId();
    }
}
