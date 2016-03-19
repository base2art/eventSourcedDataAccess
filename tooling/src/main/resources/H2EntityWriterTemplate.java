package {packageName}.data;

import lombok.Getter;
import lombok.Data;

import java.util.UUID;
import {packageName}.*;

import com.base2art.eventSourcedDataAccess.*;
import com.base2art.eventSourcedDataAccess.h2.*;

public class H2{entityTypeName}Writer
       extends H2PojoDataAccessWriter<{entityIdTypeName}, {objectDataTypeName}, {objectVersionDataTypeName}>
       implements {entityTypeName}Writer {

    public H2{entityTypeName}Writer(final H2Connector<{entityIdTypeName}> connector) {
        super(connector);
    }
}