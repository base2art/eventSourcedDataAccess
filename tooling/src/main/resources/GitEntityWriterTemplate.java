package {packageName}.data;

import lombok.Getter;
import lombok.Data;

import java.util.UUID;
import {packageName}.*;

import com.base2art.eventSourcedDataAccess.*;
import com.base2art.eventSourcedDataAccess.git.*;

public class Git{entityTypeName}Writer
       extends GitPojoDataAccessWriter<{entityIdTypeName}, {objectDataTypeName}, {objectVersionDataTypeName}>
       implements {entityTypeName}Writer {


    public Git{entityTypeName}Writer(final GitWriter<{entityIdTypeName}> writer) {
        super(writer);
    }
}