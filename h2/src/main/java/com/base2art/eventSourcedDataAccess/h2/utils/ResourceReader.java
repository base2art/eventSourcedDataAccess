package com.base2art.eventSourcedDataAccess.h2.utils;

import lombok.experimental.UtilityClass;
import lombok.val;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@UtilityClass
public class ResourceReader {

    public static String readStringUnchecked(String fileName, ClassLoader loader) {

        try {
            return readString(fileName, loader);
        }
        catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] readBytesUnchecked(String fileName, ClassLoader loader) {

        try {
            return readBytes(fileName, loader);
        }
        catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readString(String fileName, ClassLoader loader) throws URISyntaxException, IOException {

        //Get file from resources folder
        URI uri = getResourceUrl(fileName, loader).toURI();

        List<String> lines = Files.readAllLines(Paths.get(uri), Charset.defaultCharset());

        return String.join(System.lineSeparator(), lines);
    }

    public static byte[] readBytes(String fileName, ClassLoader loader) throws URISyntaxException, IOException {

        //Get file from resources folder
        URI uri = getResourceUrl(fileName, loader).toURI();

        return Files.readAllBytes(Paths.get(uri));
    }

    private static URL getResourceUrl(final String fileName, final ClassLoader loader) {

        val url = ResourceReader.class.getResource(fileName);
        if (url == null) {
            throw new IllegalStateException("Resource does not exist: " + fileName);
        }

        return url;
    }
}
