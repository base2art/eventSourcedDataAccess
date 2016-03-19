package com.base2art.eventSourcedDataAccess.tooling.commands.generation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassifiedGeneratorItemConfiguration implements ClassifiedGeneratableItem {

    private final ClassLoader cl;
    private final GeneratableItem item;

    public ClassifiedGeneratorItemConfiguration(final ClassLoader cl, GeneratableItem item) {
        this.cl = cl;
        this.item = item;
    }

    @Override
    public File getObjectFile() {
        return item.getObjectFile();
    }

    @Override
    public File getObjectVersionFile() {
        return item.getObjectVersionFile();
    }

    @Override
    public File getOutputDirectory() {
        return item.getOutputDirectory();
    }

    @Override
    public String getObjectDataClassName() {
        return item.getObjectDataClassName();
    }

    @Override
    public String getObjectVersionDataClassName() {
        return item.getObjectVersionDataClassName();
    }

    @Override
    public String getOutputClassName() {
        return item.getOutputClassName();
    }

    @Override
    public Class<?> getObjectDataClass() {

        try {
            return cl.loadClass(this.getObjectDataClassName());
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<?> getObjectVersionDataClass() {
        try {
            return cl.loadClass(this.getObjectVersionDataClassName());
        }
        catch (ClassNotFoundException e) {

            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPackageName() {

        ArrayList<String> packageNameParts = new ArrayList<>(Arrays.asList(this.getObjectDataClass().getName().split("\\.")));
        packageNameParts.remove(packageNameParts.size() - 1);
        return String.join(".", packageNameParts);
    }
}
