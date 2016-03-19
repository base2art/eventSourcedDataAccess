package {packageName};

import lombok.Getter;
import lombok.Data;

import java.util.UUID;

import com.base2art.eventSourcedDataAccess.*;

public interface {entityTypeName}Reader extends
        DataAccessReader<{entityTypeName}>,
        FilteredDataAccessReader<{entityTypeName}, {entityTypeName}FilterOptions>,
        FilteredPagedDataAccessReader<{entityIdTypeName}, {entityTypeName}, {entityTypeName}FilterOptions, {entityTypeName}OrderOptions>,
        PagedDataAccessReader<{entityIdTypeName}, {entityTypeName}, {entityTypeName}OrderOptions>,
        ItemDataAccessReader<{entityIdTypeName}, {entityTypeName}> {

}