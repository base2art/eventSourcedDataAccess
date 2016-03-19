package com.base2art.eventSourcedDataAccess.tooling.utils;

import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public class Reflection {
    public static Stream<ObjectAttribute<Method>> methods(final ClassifiedGeneratableItem item) {
        return Stream.concat(
                com.base2art.eventSourcedDataAccess.utils.Reflection.methods(item.getObjectDataClass())
                                                                    .map(x -> new ObjectAttribute<>(true, x)),
                com.base2art.eventSourcedDataAccess.utils.Reflection.methods(item.getObjectVersionDataClass())
                                                                    .map(x -> new ObjectAttribute<>(false, x)));
    }

    public static Stream<ObjectAttribute<Field>> fields(final ClassifiedGeneratableItem item) {
        return Stream.concat(
                com.base2art.eventSourcedDataAccess.utils.Reflection.fields(item.getObjectDataClass())
                                                                    .map(x -> new ObjectAttribute<>(true, x)),
                com.base2art.eventSourcedDataAccess.utils.Reflection.fields(item.getObjectVersionDataClass())
                                                                    .map(x -> new ObjectAttribute<>(false, x)));
    }
}
