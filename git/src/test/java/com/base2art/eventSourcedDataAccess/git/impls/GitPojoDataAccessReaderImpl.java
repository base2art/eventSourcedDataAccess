package com.base2art.eventSourcedDataAccess.git.impls;

import com.base2art.eventSourcedDataAccess.git.GitContainer;
import com.base2art.eventSourcedDataAccess.git.GitDataAccessConfiguration;
import com.base2art.eventSourcedDataAccess.git.GitDataAccessReader;
import com.base2art.eventSourcedDataAccess.git.GitReader;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterer;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderer;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

public class GitPojoDataAccessReaderImpl
        extends GitDataAccessReader<UUID, Person, PersonData, PersonVersionData, PersonFilterOptions, PersonOrderOptions> {

    public GitPojoDataAccessReaderImpl(
            final GitDataAccessConfiguration config,
            final String catalogType) {
        super(
                Person.class,
                PersonData.class,
                PersonVersionData.class,
                GitPojoDataAccessReaderImpl::map,
                new GitReader<>(new GitContainer<>(config, catalogType, Object::toString), new ObjectMapper(), UUID.class),
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
