package {packageName};

import lombok.Getter;
import lombok.Data;

import java.util.UUID;

public class {entityTypeName} {

    @Getter
    private final UUID id;

    {getters}

    public {entityTypeName}(final UUID uuid, final {objectDataTypeName} objectData, final {objectVersionDataTypeName} objectVersionData) {

        this.id = uuid;

        {assignments}
    }
}