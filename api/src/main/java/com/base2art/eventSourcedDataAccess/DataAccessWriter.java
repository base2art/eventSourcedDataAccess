package com.base2art.eventSourcedDataAccess;

public interface DataAccessWriter<Id, ObjectData, VersionObjectData> {

    void createObject(Id id, ObjectData object)
            throws DataAccessWriterException;

    void insertVersion(Id id, VersionObjectData version)
            throws DataAccessWriterException;

    void archiveObject(Id id)
            throws DataAccessWriterException;

}
