package {packageName};

import lombok.Getter;
import lombok.Data;

import java.util.UUID;

import com.base2art.eventSourcedDataAccess.*;

public interface {entityTypeName}Writer extends DataAccessWriter<{entityIdTypeName}, {objectDataTypeName}, {objectVersionDataTypeName}> {

}