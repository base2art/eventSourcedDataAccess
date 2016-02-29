package com.base2art.eventSourcedDataAccess.memory;

import com.base2art.eventSourcedDataAccess.DataAccessReader;
import com.base2art.eventSourcedDataAccess.DataAccessWriter;
import com.base2art.eventSourcedDataAccess.FilteredDataAccessReader;
import com.base2art.eventSourcedDataAccess.FilteredPagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.ItemDataAccessReader;
import com.base2art.eventSourcedDataAccess.PagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.memory.impls.InMemoryPojoDataAccessReaderImpl;
import com.base2art.eventSourcedDataAccess.memory.impls.InMemoryPojoDataAccessWriterImpl;
import com.base2art.eventSourcedDataAccess.testing.pojo.PojoAccessorFactory;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryPojoAccessFactory implements PojoAccessorFactory {

    private Map<UUID, Archivable<PersonData>> objectDatas = new HashMap<>();

    private Multimap<UUID, TimeStamped<PersonVersionData>> versionObjectDatas = ArrayListMultimap.create();

    @Override
    public DataAccessWriter<UUID, PersonData, PersonVersionData> writer() {

        return new InMemoryPojoDataAccessWriterImpl(objectDatas, versionObjectDatas);
    }

    @Override
    public ItemDataAccessReader<UUID, Person> itemReader() {

        return new InMemoryPojoDataAccessReaderImpl(objectDatas, versionObjectDatas);
    }

    @Override
    public DataAccessReader<Person> setReader() {
        return new InMemoryPojoDataAccessReaderImpl(objectDatas, versionObjectDatas);
    }

    @Override
    public FilteredDataAccessReader<Person, PersonFilterOptions> filteredSetReader() {
        return new InMemoryPojoDataAccessReaderImpl(objectDatas, versionObjectDatas);
    }

    @Override
    public FilteredPagedDataAccessReader<UUID, Person, PersonFilterOptions, PersonOrderOptions> filteredPagedSetReader() {
        return new InMemoryPojoDataAccessReaderImpl(objectDatas, versionObjectDatas);
    }

    @Override
    public PagedDataAccessReader<UUID, Person, PersonOrderOptions> pagedSetReader() {
        return new InMemoryPojoDataAccessReaderImpl(objectDatas, versionObjectDatas);
    }

    @Override
    public void destroy() {

    }
}
