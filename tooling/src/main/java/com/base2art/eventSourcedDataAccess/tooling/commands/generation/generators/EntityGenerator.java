package com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators;

import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;
import com.base2art.eventSourcedDataAccess.tooling.resx.Resources;
import com.base2art.eventSourcedDataAccess.tooling.utils.ObjectAttribute;
import com.base2art.eventSourcedDataAccess.tooling.utils.Reflection;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntityGenerator implements Generator {

    @Override
    public GeneratorContent generate(final ClassifiedGeneratableItem item) {

        List<String> getters = new ArrayList<>();
        List<String> assignments = new ArrayList<>();
        getters.add("");
        assignments.add("");

        List<ObjectAttribute<Method>> methods = Reflection.methods(item)
                                                          .collect(Collectors.toList());

        Reflection.fields(item)
                  .collect(Collectors.toList())

                  .forEach(pair -> {
                      getters.add("    @Getter");

                      Field x = pair.getAttribute();

                      assignments.add(String.format(
                              "        this.%s = %s.%s();",
                              x.getName(),
                              pair.isObjectProperty() ? "objectData" : "objectVersionData",

                              methods.stream().filter(z ->
                                                              x.getName()
                                                               .equalsIgnoreCase(
                                                                       z.getAttribute().getName())
                                                              ||
                                                              ("get" + x.getName())
                                                                      .equalsIgnoreCase(
                                                                              z.getAttribute().getName()))
                                     .findFirst()
                                     .get()
                                     .getAttribute()
                                     .getName()));

                      if (x.getType().isArray()) {

                          getters.add("    private final " +
                                      x.getType().getComponentType().getName() +
                                      "[] " + x.getName() + ";");
                      }
                      else {

                          final TypeVariable<? extends Class<?>>[] typeParameters = x
                                  .getType()
                                  .getTypeParameters();
                          if (typeParameters == null ||
                              typeParameters.length == 0) {

                              getters.add("    private final " +
                                          x.getType().getName() + " " +
                                          x.getName() + ";");
                          }
                          else {
                              getters.add(
                                      "    private final " + x.getGenericType() +
                                      " " + x.getName() + ";");
                          }
                      }

                      getters.add("");
                      assignments.add("");
                  });

        String content = Resources.entityTemplate(
                String.join(".", item.getPackageName()),
                item.getOutputClassName(),
                item.getObjectDataClass().getSimpleName(),
                item.getObjectVersionDataClass().getSimpleName(),
                getters,
                assignments);

        return new GeneratorContent() {
            @Override
            public File getOutputDirectory() {
                return item.getOutputDirectory();
            }

            @Override
            public String getFileName() {
                return item.getOutputClassName() + ".java";
            }

            @Override
            public String getContent() {
                return content;
            }
        };
    }
}
