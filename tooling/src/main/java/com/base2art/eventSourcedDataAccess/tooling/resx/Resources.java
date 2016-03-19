package com.base2art.eventSourcedDataAccess.tooling.resx;

import com.base2art.eventSourcedDataAccess.utils.ResourceReader;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class Resources {

    public static String entityTemplate(
            final String packageName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName,
            final List<String> getters,
            final List<String> assignments) {

        return ResourceReader.readStringUnchecked("/EntityTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "")
                             .replace("{assignments}", String.join(System.lineSeparator(), assignments))
                             .replace("{getters}", String.join(System.lineSeparator(), getters));
    }

    public static String entityReaderTemplate(
            final String packageName,
            final String entityIdTypeName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName) {

        return ResourceReader.readStringUnchecked("/EntityReaderTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{entityIdTypeName}", entityIdTypeName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "");
    }

    public static String entityWriterTemplate(
            final String packageName,
            final String entityIdTypeName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName) {

        return ResourceReader.readStringUnchecked("/EntityWriterTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{entityIdTypeName}", entityIdTypeName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "");
    }

    public static String entityFilterOptionsTemplate(
            final String packageName,
            final String entityName,
            final String filterOptions) {
        return ResourceReader.readStringUnchecked("/EntityFilterOptionsTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{filterOptions}", filterOptions)
                             .replace("java.lang.", "");
    }

    public static String entityOrderOptionsTemplate(
            final String packageName,
            final String entityName,
            final String orderOptions) {
        return ResourceReader.readStringUnchecked("/EntityOrderOptionsTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{orderOptions}", orderOptions)
                             .replace("java.lang.", "");
    }


    public static String entityInMemoryReaderTemplate(
            final String packageName,
            final String entityIdTypeName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName) {

        return ResourceReader.readStringUnchecked("/InMemoryEntityReaderTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{entityIdTypeName}", entityIdTypeName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "");
    }

    public static String entityH2ReaderTemplate(
            final String packageName,
            final String entityIdTypeName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName) {

        return ResourceReader.readStringUnchecked("/H2EntityReaderTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{entityIdTypeName}", entityIdTypeName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "");
    }

    public static String entityGitReaderTemplate(
            final String packageName,
            final String entityIdTypeName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName) {

        return ResourceReader.readStringUnchecked("/GitEntityReaderTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{entityIdTypeName}", entityIdTypeName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "");
    }


    public static String entityInMemoryWriterTemplate(
            final String packageName,
            final String entityIdTypeName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName) {

        return ResourceReader.readStringUnchecked("/InMemoryEntityWriterTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{entityIdTypeName}", entityIdTypeName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "");
    }

    public static String entityH2WriterTemplate(
            final String packageName,
            final String entityIdTypeName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName) {

        return ResourceReader.readStringUnchecked("/H2EntityWriterTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{entityIdTypeName}", entityIdTypeName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "");
    }

    public static String entityGitWriterTemplate(
            final String packageName,
            final String entityIdTypeName,
            final String entityName,
            final String objectDataTypeName,
            final String objectVersionDataTypeName) {

        return ResourceReader.readStringUnchecked("/GitEntityWriterTemplate.java", Resources.class.getClassLoader())
                             .replace("{packageName}", String.join(".", packageName))
                             .replace("{entityTypeName}", entityName)
                             .replace("{entityIdTypeName}", entityIdTypeName)
                             .replace("{objectDataTypeName}", objectDataTypeName)
                             .replace("{objectVersionDataTypeName}", objectVersionDataTypeName)
                             .replace("java.lang.", "");
    }
}
