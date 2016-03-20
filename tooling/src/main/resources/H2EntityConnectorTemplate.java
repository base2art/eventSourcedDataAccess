package {packageName}.data;

import lombok.Getter;
import lombok.Data;

import java.util.UUID;
import {packageName}.*;

import com.base2art.eventSourcedDataAccess.*;
import com.base2art.eventSourcedDataAccess.h2.*;

public class H2{entityTypeName}Connector
       extends H2Connector<{entityIdTypeName}> {

    public H2{entityTypeName}Connector(
        final String location,
        final String user,
        final String password,
        final String catalogName) {

        super(
                location,
                user,
                password,
                catalogName,
                {entityIdTypeName}.class,
                {objectDataTypeName}.class,
                {objectVersionDataTypeName}.class);
    }

    public H2{entityTypeName}Connector(
        final String location,
        final String user,
        final String password) {

        this(
            location,
            user,
            password,
            {objectDataTypeName}.class.getSimpleName());
    }
}