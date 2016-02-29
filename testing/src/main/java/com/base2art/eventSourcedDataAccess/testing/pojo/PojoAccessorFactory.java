package com.base2art.eventSourcedDataAccess.testing.pojo;

import com.base2art.eventSourcedDataAccess.DataAccessReader;
import com.base2art.eventSourcedDataAccess.DataAccessWriter;
import com.base2art.eventSourcedDataAccess.FilteredDataAccessReader;
import com.base2art.eventSourcedDataAccess.FilteredPagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.ItemDataAccessReader;
import com.base2art.eventSourcedDataAccess.PagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;

import java.util.UUID;

public interface PojoAccessorFactory {

    DataAccessWriter<UUID, PersonData, PersonVersionData> writer();

    ItemDataAccessReader<UUID, Person> itemReader();

    DataAccessReader<Person> setReader();

    FilteredDataAccessReader<Person, PersonFilterOptions> filteredSetReader();

    FilteredPagedDataAccessReader<UUID, Person, PersonFilterOptions, PersonOrderOptions> filteredPagedSetReader();

    PagedDataAccessReader<UUID, Person, PersonOrderOptions> pagedSetReader();

    void destroy();
}
