package com.base2art.eventSourcedDataAccess.tooling.commands.generation;

public interface GeneratableItem {
    java.io.File getObjectFile();

    java.io.File getObjectVersionFile();

    java.io.File getOutputDirectory();

    String getObjectDataClassName();

    String getObjectVersionDataClassName();

    String getOutputClassName();
}
