package {packageName}.data;

import lombok.Getter;
import lombok.Data;

import java.util.UUID;
import {packageName}.*;

import com.base2art.eventSourcedDataAccess.*;
import com.base2art.eventSourcedDataAccess.memory.*;

public class InMemory{entityTypeName}Reader
       extends InMemoryPojoDataAccessReader<{entityIdTypeName}, {entityTypeName}, {objectDataTypeName}, {objectVersionDataTypeName}, {entityTypeName}FilterOptions, {entityTypeName}OrderOptions>
       implements {entityTypeName}Reader {


    public InMemory{entityTypeName}Reader(
        final java.util.Map<{entityIdTypeName}, Archivable<{objectDataTypeName}>> objectData,
        final com.google.common.collect.Multimap<{entityIdTypeName}, TimeStamped<{objectVersionDataTypeName}>> versionObjectData) {
        super(
           {entityTypeName}.class,
            InMemory{entityTypeName}Reader::createObject,
            objectData,
            versionObjectData,
            new com.base2art.eventSourcedDataAccess.conventional.FieldFilterer<>(),
            new com.base2art.eventSourcedDataAccess.conventional.FieldEnumOrderer<>({entityTypeName}.class));
        }


    @Override
    protected {entityIdTypeName} getIdForEntity(final {entityTypeName} x) {
        return x.getId();
    }

    protected static {entityTypeName} createObject({entityIdTypeName} id, {objectDataTypeName} objectData, {objectVersionDataTypeName} objectVersionData) {
        return new {entityTypeName}(id, objectData, objectVersionData);
    }
}