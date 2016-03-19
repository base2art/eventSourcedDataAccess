package com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators;

import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;
import com.base2art.eventSourcedDataAccess.tooling.resx.Resources;

import java.util.UUID;

public class EntityReaderGenerator implements Generator {
    @Override
    public GeneratorContent generate(final ClassifiedGeneratableItem item) {
        return new GeneratorContent() {
            @Override
            public String getFileName() {
                return item.getOutputClassName() + "Reader.java";
            }

            @Override
            public String getContent() {

                return Resources.entityReaderTemplate(
                        item.getPackageName(),
                        UUID.class.getTypeName(),
                        item.getOutputClassName().toString(),
                        item.getObjectDataClass().getSimpleName(),
                        item.getObjectVersionDataClass().getSimpleName());
            }
        };
    }
}
