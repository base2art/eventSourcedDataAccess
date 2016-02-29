package com.base2art.eventSourcedDataAccess.git.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class IOUtils {

    private IOUtils() {}

    public static void deleteRecursive(final File f) {

        try {
            delete(f);
        }
        catch (IOException e) {
            try {
                delete(f);
            }
            catch (IOException e2) {
                System.out.println(e2);
            }
        }
    }

    private static void delete(File f) throws IOException {
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                delete(c);
            }
        }

        if (!f.delete()) {
            throw new FileNotFoundException("Failed to delete file: " + f);
        }
    }
}