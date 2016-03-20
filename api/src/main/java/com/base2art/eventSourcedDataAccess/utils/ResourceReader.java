package com.base2art.eventSourcedDataAccess.utils;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
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
        @Cleanup InputStream in = getResourceUrl(fileName, loader);
        java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static byte[] readBytes(String fileName, ClassLoader loader) throws URISyntaxException, IOException {

        //Get file from resources folder
        @Cleanup InputStream in = getResourceUrl(fileName, loader);

        return getBytesFromInputStream(in);
    }

    private static InputStream getResourceUrl(final String fileName, final ClassLoader loader) {

        val url = loader.getResourceAsStream(fileName);
        if (url != null) {
            return url;
        }

        val urlByClass = ResourceReader.class.getResourceAsStream(fileName);
        if (urlByClass != null) {

            return urlByClass;
        }

        throw new IllegalStateException("Resource does not exist: " + fileName);
    }

    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        @Cleanup ByteArrayOutputStream os = new ByteArrayOutputStream();

        byte[] buffer = new byte[0xFFFF];

        for (int len; (len = is.read(buffer)) != -1; ) {
            os.write(buffer, 0, len);
        }

        os.flush();

        return os.toByteArray();
    }
}
