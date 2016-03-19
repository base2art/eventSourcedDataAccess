package com.base2art.eventSourcedDataAccess.tooling.commands.generation;

import lombok.Data;

import java.io.File;

@Data
public class GeneratorItemConfiguration implements GeneratableItem {

    private File objectFile;

    private File objectVersionFile;

    private File outputDirectory;

    private String objectDataClassName;

    private String objectVersionDataClassName;

    private String outputClassName;
}
