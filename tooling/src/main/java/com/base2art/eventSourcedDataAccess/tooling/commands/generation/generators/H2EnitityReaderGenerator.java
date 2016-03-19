package com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators;

import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;
import com.base2art.eventSourcedDataAccess.tooling.resx.Resources;

import java.io.File;
import java.util.UUID;

public class H2EnitityReaderGenerator implements Generator {
    @Override
    public GeneratorContent generate(final ClassifiedGeneratableItem item) {
        return new GeneratorContent() {
            @Override
            public File getOutputDirectory() {
                return new File(item.getOutputDirectory(), "data");
            }

            @Override
            public String getFileName() {
                return "H2" + item.getOutputClassName() + "Reader.java";
            }

            @Override
            public String getContent() {

                return Resources.entityH2ReaderTemplate(
                        item.getPackageName(),
                        UUID.class.getTypeName(),
                        item.getOutputClassName(),
                        item.getObjectDataClass().getSimpleName(),
                        item.getObjectVersionDataClass().getSimpleName());
            }
        };
    }
}
