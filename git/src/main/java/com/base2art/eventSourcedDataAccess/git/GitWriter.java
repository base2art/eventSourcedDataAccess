package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GitWriter<Id> {

    private final GitContainer<Id> container;
    private final ObjectMapper mapper;
//    private AtomicBoolean isPushing = new AtomicBoolean();

    public GitWriter(final GitContainer<Id> container, ObjectMapper mapper) {
        this.container = container;
        this.mapper = mapper;
    }

    public final <T> void writeObject(final Id id, final String fileName, T objectToSerialize)
            throws DataAccessWriterException {

        File objectContainer = container.get(id, true);

        File objectData = new File(objectContainer, fileName);
        if (objectData.exists()) {
            throw new DataAccessWriterException("Object already exists: " + objectData.getAbsolutePath());
        }

        writeAndCommit(objectToSerialize, objectData);
        this.container.pushAsync();
    }

    public void ensureUpdated() {
        this.container.ensureUpdated();
    }

    public void ensureUpdatedAsync() {
        this.container.ensureUpdatedAsync();
    }

    public boolean hasObject(final Id id) {
        return this.container.hasObject(id);
    }

    private synchronized <T> void writeAndCommit(final T objectToSerialize, final File objectData)
            throws DataAccessWriterException {

        try {
            mapper.writeValue(objectData, objectToSerialize);
        }
        catch (IOException e) {
            throw new DataAccessWriterException(e);
        }

        this.container.commit();
    }
}
