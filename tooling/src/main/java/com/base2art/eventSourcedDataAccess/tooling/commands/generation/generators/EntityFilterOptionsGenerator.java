package com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators;

import com.base2art.eventSourcedDataAccess.dataTypes.DataTypeFieldRegistrar;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;
import com.base2art.eventSourcedDataAccess.tooling.resx.Resources;
import com.base2art.eventSourcedDataAccess.tooling.utils.ObjectAttribute;
import com.base2art.eventSourcedDataAccess.tooling.utils.Reflection;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

public class EntityFilterOptionsGenerator implements Generator {
    @Override
    public GeneratorContent generate(final ClassifiedGeneratableItem item) {
        return new GeneratorContent() {
            @Override
            public String getFileName() {
                return item.getOutputClassName() + "FilterOptions.java";
            }

            @Override
            public String getContent() {

                List<String> fields = Reflection.fields(item)
                                                .map(ObjectAttribute::getAttribute)
                                                .filter(x -> DataTypeFieldRegistrar.hasType(x.getType()))
                                                .map(x -> new Tuple<>(x, DataTypeFieldRegistrar.getField(x.getType())))
                                                .map(x -> String.format(
                                                        "private final %s %s = new %s();",
                                                        x.getItem2().getFieldInterface().getName(),
                                                        x.getItem1().getName(),
                                                        x.getItem2().getFieldClass().getName()))
                                                .collect(Collectors.toList());

                return Resources.entityFilterOptionsTemplate(
                        item.getPackageName(),
                        item.getOutputClassName(),
                        String.join("," + System.lineSeparator(), fields));
            }
        };
    }

    @Data
    private class Tuple<T, T2> {

        private final T item1;
        private final T2 item2;
    }
}
