package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GitReader<Id> {
    private final GitContainer<Id> container;
    private final ObjectMapper mapper;
    private final Class<Id> idType;

    public GitReader(GitContainer<Id> container, ObjectMapper mapper, Class<Id> idType) {
        this.container = container;
        this.mapper = mapper;
        this.idType = idType;
    }

    public File getCatalog() throws DataAccessWriterException {
        return this.container.getCatalog();
    }

    public Id parseId(final String id) {

        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        try {
            String data = mapper.writeValueAsString(map);

            data = data.substring(data.indexOf(':') + 1);

            data = data.substring(0, data.lastIndexOf('}'));
            return mapper.readValue(data, this.idType);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <ObjectData> ObjectData getObjectData(final Id id, final Class<ObjectData> objectDataType) throws DataAccessReaderException {

        try {
            File objectContainer = container.get(id, true);


            File objectData = new File(objectContainer, ".meta");

            return mapper.readValue(objectData, objectDataType);

        }

        catch (IOException | DataAccessWriterException e) {
            throw new DataAccessReaderException(e);
        }
    }

    public <VersionObjectData> VersionObjectData getLatestVersionObjectDataById(final Id id, final Class<VersionObjectData> objectDataType)
            throws DataAccessReaderException {

        try {
            File objectContainer = container.get(id, true);


            File[] items = objectContainer.listFiles((x) -> !x.getName().startsWith(".") && x.isFile());
            if (items.length == 0) {
                return null;
            }

            File versionFile = items[items.length - 1];

            return mapper.readValue(versionFile, objectDataType);
        }
        catch (IOException | DataAccessWriterException e) {
            throw new DataAccessReaderException(e);
        }
    }

    public boolean hasObject(final Id id) {
        return this.container.hasObject(id);
    }
}
