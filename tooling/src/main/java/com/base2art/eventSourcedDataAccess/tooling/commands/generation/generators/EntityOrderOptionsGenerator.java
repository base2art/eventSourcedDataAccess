package com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators;

import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeFieldRegistrar;
import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeRegistrar;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;
import com.base2art.eventSourcedDataAccess.tooling.resx.Resources;
import com.base2art.eventSourcedDataAccess.tooling.utils.Reflection;

import java.util.ArrayList;
import java.util.List;

public class EntityOrderOptionsGenerator implements Generator {
    @Override
    public GeneratorContent generate(final ClassifiedGeneratableItem item) {
        return new GeneratorContent() {
            @Override
            public String getFileName() {
                return item.getOutputClassName() + "OrderOptions.java";
            }

            @Override
            public String getContent() {

                List<String> fields = new ArrayList<>();

                Reflection.fields(item)
                          .map(x -> x.getAttribute())
                          .filter(x -> DataTypeRegistrar.hasType(x.getType()))
                          .map(x -> x.getName())
                          .map(x -> Character.toUpperCase(x.charAt(0)) + x.substring(1))
                          .forEach(x -> {
                              fields.add(x + "Ascending");
                              fields.add(x + "Descending");
                          });

                return Resources.entityOrderOptionsTemplate(
                        item.getPackageName(),
                        item.getOutputClassName(),
                        String.join("," + System.lineSeparator(), fields));
            }
        };
    }
}
