package {packageName}.data;

import lombok.Getter;
import lombok.Data;

import java.util.UUID;
import {packageName}.*;

import com.base2art.eventSourcedDataAccess.*;
import com.base2art.eventSourcedDataAccess.git.*;

public class Git{entityTypeName}Reader
       extends GitPojoDataAccessReader<{entityIdTypeName}, {entityTypeName}, {objectDataTypeName}, {objectVersionDataTypeName}, {entityTypeName}FilterOptions, {entityTypeName}OrderOptions>
       implements {entityTypeName}Reader {


    public Git{entityTypeName}Reader(final GitReader<{entityIdTypeName}> reader) {
        super(
            {entityTypeName}.class,
            {objectDataTypeName}.class,
            {objectVersionDataTypeName}.class,
            Git{entityTypeName}Reader::createObject,
            reader,
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