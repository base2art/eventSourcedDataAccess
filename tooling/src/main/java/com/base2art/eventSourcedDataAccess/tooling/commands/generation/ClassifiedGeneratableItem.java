package com.base2art.eventSourcedDataAccess.tooling.commands.generation;

public interface ClassifiedGeneratableItem extends GeneratableItem {
    Class<?> getObjectDataClass();

    Class<?> getObjectVersionDataClass();

    String getPackageName();
}
