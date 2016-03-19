package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReader;
import com.base2art.eventSourcedDataAccess.DataAccessWriter;
import com.base2art.eventSourcedDataAccess.FilteredDataAccessReader;
import com.base2art.eventSourcedDataAccess.FilteredPagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.ItemDataAccessReader;
import com.base2art.eventSourcedDataAccess.PagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.h2.impls.H2PojoDataAccessReaderImpl;
import com.base2art.eventSourcedDataAccess.h2.impls.H2PojoDataAccessWriterImpl;
import com.base2art.eventSourcedDataAccess.testing.pojo.PojoAccessorFactory;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class H2PojoAccessFactory implements PojoAccessorFactory {

    private final File file;
    private H2Connector<UUID> connector;

    public H2PojoAccessFactory() {
        try {

            this.file = File.createTempFile("test", ".h2");
            System.out.println(this.file.getAbsolutePath());
            this.connector = new H2Connector<>(
                    file.getAbsolutePath(),
                    PersonData.class.getSimpleName(),
                    UUID.class,
                    PersonData.class,
                    PersonVersionData.class);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DataAccessWriter<UUID, PersonData, PersonVersionData> writer() {

        return new H2PojoDataAccessWriterImpl(this.connector);
    }

    @Override
    public ItemDataAccessReader<UUID, Person> itemReader() {

        return new H2PojoDataAccessReaderImpl(this.connector);
    }

    @Override
    public DataAccessReader<Person> setReader() {
        return new H2PojoDataAccessReaderImpl(this.connector);
    }

    @Override
    public FilteredDataAccessReader<Person, PersonFilterOptions> filteredSetReader() {
        return new H2PojoDataAccessReaderImpl(this.connector);
    }

    @Override
    public FilteredPagedDataAccessReader<UUID, Person, PersonFilterOptions, PersonOrderOptions> filteredPagedSetReader() {
        return new H2PojoDataAccessReaderImpl(this.connector);
    }

    @Override
    public PagedDataAccessReader<UUID, Person, PersonOrderOptions> pagedSetReader() {
        return new H2PojoDataAccessReaderImpl(this.connector);
    }

    @Override
    public void destroy() {
        this.file.delete();
    }
}
