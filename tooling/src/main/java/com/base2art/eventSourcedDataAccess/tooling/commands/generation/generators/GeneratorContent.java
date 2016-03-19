package com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators;

import java.io.File;

public interface GeneratorContent {

    File getOutputDirectory();

    String getFileName();

    String getContent();
}
