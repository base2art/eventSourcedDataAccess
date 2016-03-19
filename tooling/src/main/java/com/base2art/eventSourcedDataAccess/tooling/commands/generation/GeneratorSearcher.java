package com.base2art.eventSourcedDataAccess.tooling.commands.generation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GeneratorSearcher {

    public List<GeneratableItem> find(File sourceDir, File targetDir) {

        final List<GeneratableItem> files = new ArrayList<>();

        this.findPairs(targetDir, sourceDir, null, files);
        return files;
    }

    private void findPairs(final File baseClassDirectory, final File baseSrcDirectory, final String path, final List<GeneratableItem> files) {

        if (baseClassDirectory == null) {
            return;
        }

        List<String> names = Arrays.stream(baseClassDirectory.listFiles(File::isFile))
                                   .map(File::getName)
                                   .collect(Collectors.toList());

        Set<String> fileNames = new HashSet<>(names);

        for (File child : baseClassDirectory.listFiles(File::isFile)) {

            String childName = child.getName();
            if (childName.endsWith("VersionData.class")) {
                childName = childName.substring(0, childName.length() - "VersionData.class".length());
            }

            if (fileNames.contains(childName + "Data.class")) {
                GeneratorItemConfiguration config = new GeneratorItemConfiguration();
                config.setObjectFile(new File(child.getParent(), childName + "Data.class"));
                config.setObjectVersionFile(child);
                config.setOutputClassName(childName);

                String newPath = path;
                if (!(newPath == null || newPath.isEmpty())) {
                    newPath = newPath + ".";
                }
                config.setObjectDataClassName(newPath + childName + "Data");
                config.setObjectVersionDataClassName(newPath + childName + "VersionData");
                config.setOutputDirectory(baseSrcDirectory);

                files.add(config);
            }
        }

        for (File child : baseClassDirectory.listFiles(File::isDirectory)) {
            String name = child.getName();
            if (!(path == null || path.isEmpty())) {
                name = path + "." + name;
            }

            findPairs(child, new File(baseSrcDirectory, child.getName()), name, files);
        }
    }
}
