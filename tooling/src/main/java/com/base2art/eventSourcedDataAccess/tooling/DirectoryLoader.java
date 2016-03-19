package com.base2art.eventSourcedDataAccess.tooling;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

public class DirectoryLoader extends ClassLoader {
    private Hashtable<String, Class<?>> cache;
    private File root;

    public DirectoryLoader(File root) {
        this.cache = new Hashtable<>();
        if (root != null && root.isDirectory()) {
            this.root = root;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    private DirectoryLoader() {
    }

    public Class<?> loadClass(String var1) throws ClassNotFoundException {
        return this.loadClass(var1, true);
    }

    public synchronized Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> cachedClass = this.cache.get(name);
        if (cachedClass != null) {
            return cachedClass;
        }

        try {
            return super.findSystemClass(name);
        }
        catch (ClassNotFoundException var6) {

            byte[] classFileData = this.getClassFileData(name);
            if (classFileData == null) {
                throw new ClassNotFoundException();
            }

            Class<?> craftedClass = this.defineClass(name, classFileData, 0, classFileData.length);
            if (craftedClass == null) {
                throw new ClassFormatError();
            }

            if (resolve) {
                this.resolveClass(craftedClass);
            }

            this.cache.put(name, craftedClass);
            return craftedClass;
        }
    }

    private byte[] getClassFileData(String name) {
        byte[] content = null;
        FileInputStream fileInputStream = null;
        File diskPath = new File(this.root, name.replace('.', File.separatorChar) + ".class");

        try {

            fileInputStream = new FileInputStream(diskPath);
            content = new byte[fileInputStream.available()];
            fileInputStream.read(content);
        }
        catch (ThreadDeath threadDeath) {
            throw threadDeath;
        }
        catch (Throwable throwable) {

        }
        finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                }
                catch (ThreadDeath var17) {
                    throw var17;
                }
                catch (Throwable var18) {
                }
            }
        }

        return content;
    }
}