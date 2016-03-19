package com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators;

import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;
import com.base2art.eventSourcedDataAccess.tooling.resx.Resources;

import java.io.File;
import java.util.UUID;

public class EntityWriterGenerator implements Generator {
    @Override
    public GeneratorContent generate(final ClassifiedGeneratableItem item) {
        return new GeneratorContent() {
            @Override
            public File getOutputDirectory() {
                return item.getOutputDirectory();
            }

            @Override
            public String getFileName() {
                return item.getOutputClassName() + "Writer.java";
            }

            @Override
            public String getContent() {

                return Resources.entityWriterTemplate(
                        item.getPackageName(),
                        UUID.class.getTypeName(),
                        item.getOutputClassName(),
                        item.getObjectDataClass().getSimpleName(),
                        item.getObjectVersionDataClass().getSimpleName());
            }
        };
    }
}