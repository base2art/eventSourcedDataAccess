package {packageName}.data;

import lombok.Getter;
import lombok.Data;

import java.util.UUID;
import {packageName}.*;

import com.base2art.eventSourcedDataAccess.*;
import com.base2art.eventSourcedDataAccess.memory.*;

public class InMemory{entityTypeName}Writer
       extends InMemoryPojoDataAccessWriter<{entityIdTypeName}, {objectDataTypeName}, {objectVersionDataTypeName}>
       implements {entityTypeName}Writer {


    public InMemory{entityTypeName}Writer(
        final java.util.Map<{entityIdTypeName}, Archivable<{objectDataTypeName}>> objectData,
        final com.google.common.collect.Multimap<{entityIdTypeName}, TimeStamped<{objectVersionDataTypeName}>> versionObjectData){
        super(objectData,versionObjectData);
    }
}