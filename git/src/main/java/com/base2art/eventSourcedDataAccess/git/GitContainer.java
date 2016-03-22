package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.DataAccessWriterException;

import java.io.File;
import java.util.function.Function;

import static com.base2art.eventSourcedDataAccess.git.Util.getWorkingDir;

public class GitContainer<Id> {

    private final GitDataAccessConfiguration config;

    private final String catalogType;

    private final Function<Id, String> idToContainerMap;

    private final GitMessageQueue messageQueue;

    public GitContainer(
            final GitDataAccessConfiguration config,
            final GitMessageQueue messageQueue,
            final String catalogType,
            final Function<Id, String> idToContainerMap) {
        this.messageQueue = messageQueue;
        this.config = config;
        this.catalogType = catalogType;
        this.idToContainerMap = idToContainerMap;
    }

    public File get(final Id id, final boolean createIfNotExist)
            throws DataAccessWriterException {

        File catalog = getCatalog(true);

        File objectContainer = new File(catalog, this.idToContainerMap.apply(id));
        if (!objectContainer.exists()) {
            if (createIfNotExist) {
                boolean created = objectContainer.mkdir();
                if (!created) {
                    throw new DataAccessWriterException("Unable to create dir: " + objectContainer.getAbsolutePath());
                }
            }
        }

        return objectContainer;
    }

    public boolean hasObject(final Id id) {

        this.ensureUpdatedAsync();

        try {
            File objectContainer = this.get(id, false);
            return objectContainer.exists();
        }
        catch (DataAccessWriterException e) {
            return false;
        }
    }

    public void commit() {

        messageQueue.addMessage(MessageType.Commit);
    }

    public void pushAsync() {

        messageQueue.addMessage(MessageType.Push);
    }

    public void ensureUpdated() {

        messageQueue.pushMessageNow(MessageType.Push);
    }

    public final void ensureUpdatedAsync() {
        messageQueue.addMessage(MessageType.Pull);
    }

    protected File getCatalog(boolean allowDirtyReads) throws DataAccessWriterException {

        File catalog = new File(getWorkingDir(this.config), catalogType);
        if (!catalog.exists()) {

            if (!catalog.getParentFile().exists()) {
                catalog.getParentFile().mkdirs();
            }

            if (allowDirtyReads) {
                this.ensureUpdatedAsync();
            }
            else {
                this.ensureUpdated();
            }

            if (!catalog.exists()) {
                boolean created = catalog.mkdir();
                if (!created) {
                    throw new DataAccessWriterException("Unable to create dir: " + catalog.getAbsolutePath());
                }
            }
        }

        return catalog;
    }
}
