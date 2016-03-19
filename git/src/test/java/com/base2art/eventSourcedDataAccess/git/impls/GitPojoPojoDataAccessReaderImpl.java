package com.base2art.eventSourcedDataAccess.git.impls;

import com.base2art.eventSourcedDataAccess.conventional.FieldEnumOrderer;
import com.base2art.eventSourcedDataAccess.conventional.FieldFilterer;
import com.base2art.eventSourcedDataAccess.git.GitContainer;
import com.base2art.eventSourcedDataAccess.git.GitDataAccessConfiguration;
import com.base2art.eventSourcedDataAccess.git.GitPojoDataAccessReader;
import com.base2art.eventSourcedDataAccess.git.GitReader;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

public class GitPojoPojoDataAccessReaderImpl
        extends GitPojoDataAccessReader<UUID, Person, PersonData, PersonVersionData, PersonFilterOptions, PersonOrderOptions> {

    public GitPojoPojoDataAccessReaderImpl(
            final GitDataAccessConfiguration config,
            final String catalogType) {
        super(
                Person.class,
                PersonData.class,
                PersonVersionData.class,
                GitPojoPojoDataAccessReaderImpl::map,
                new GitReader<>(new GitContainer<>(config, catalogType, Object::toString), new ObjectMapper(), UUID.class),
                new FieldFilterer<>(),
                new FieldEnumOrderer<>(Person.class));
    }

    private static Person map(final UUID uuid, final PersonData personData, final PersonVersionData personVersionData) {
        return new Person(uuid, personData, personVersionData);
    }

    @Override
    protected UUID getIdForEntity(final Person x) {
        return x.getId();
    }
}
