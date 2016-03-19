package com.base2art.eventSourcedDataAccess.h2;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.base2art.eventSourcedDataAccess.impls.PojoDataAccessWriterBase;
import lombok.Data;
import lombok.val;

import java.util.Arrays;

import static com.base2art.eventSourcedDataAccess.h2.H2Writes.insertRecord;

public class H2PojoDataAccessWriter<Id, ObjectData, VersionObjectData>
        extends PojoDataAccessWriterBase<Id, ObjectData, VersionObjectData> {

    private final H2Connector<Id> connector;

    public H2PojoDataAccessWriter(H2Connector connector) {

        this.connector = connector;
    }

    @Override
    protected boolean hasObject(final Id id)
            throws DataAccessWriterException {

        try {

            String sql = "SELECT COUNT(*) as value FROM " + this.connector.objectTable() + " WHERE object_id = ?";
            val result = H2Queries.<Id, IntHolder>fetchSingleObject(
                    this.connector, id, sql, Arrays.asList(IntHolder.class.getDeclaredFields()), x -> new IntHolder());
            return result.isPresent() && result.get().value > 0;
        }
        catch (DataAccessReaderException e) {
            throw new DataAccessWriterException(e);
        }
    }

    @Override
    protected void insertVersionInternal(final Id id, final VersionObjectData version)
            throws DataAccessWriterException {

        insertRecord(
                this.connector,
                this.connector.objectVersionTable(),
                id,
                version,
                this.connector.nonFinalObjectVersionDataFields(),
                "object_id",
                "?");
    }

    @Override
    protected void createObjectInternal(final Id id, final ObjectData object)
            throws DataAccessWriterException {

        insertRecord(
                this.connector,
                this.connector.objectTable(),
                id,
                object,
                this.connector.nonFinalObjectDataFields(),
                "object_id",
                "?");
    }

    @Override
    protected void archiveObjectInternal(final Id id) throws DataAccessWriterException {

        insertRecord(
                this.connector,
                this.connector.objectStatusTable(),
                id,
                new ArchivableObject(),
                Arrays.asList(ArchivableObject.class.getDeclaredFields()),
                "object_id",
                "?");
    }

    @Data
    private static class ArchivableObject {
        private boolean isArchived = true;
    }

    private static class IntHolder {
        public int value;
    }
}