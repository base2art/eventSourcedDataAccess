package {packageName}.data;

import lombok.Getter;
import lombok.Data;

import java.util.UUID;
import {packageName}.*;

import com.base2art.eventSourcedDataAccess.*;
import com.base2art.eventSourcedDataAccess.h2.*;

public class H2{entityTypeName}Reader
       extends H2PojoDataAccessReader<{entityIdTypeName}, {entityTypeName}, {objectDataTypeName}, {objectVersionDataTypeName}, {entityTypeName}FilterOptions, {entityTypeName}OrderOptions>
       implements {entityTypeName}Reader {

    public H2{entityTypeName}Reader(final H2Connector<{entityIdTypeName}> connector) {
        super(
            {entityTypeName}.class,
            H2{entityTypeName}Reader::createObject,
            connector);
    }

    @Override
    public {objectVersionDataTypeName} createVersionObjectData(final {entityIdTypeName} uuid) {
        return new {objectVersionDataTypeName}();
    }

    @Override
    public {objectDataTypeName} createObjectData(final {entityIdTypeName} id) {
        return new {objectDataTypeName}();
    }

    protected static {entityTypeName} createObject({entityIdTypeName} id, {objectDataTypeName} objectData, {objectVersionDataTypeName} objectVersionData) {
        return new {entityTypeName}(id, objectData, objectVersionData);
    }
}