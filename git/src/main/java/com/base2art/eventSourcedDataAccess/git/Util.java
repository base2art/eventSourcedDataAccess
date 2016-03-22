package com.base2art.eventSourcedDataAccess.git;

import lombok.experimental.UtilityClass;

import java.io.File;

@UtilityClass
public class Util {

    public static File getWorkingDir(GitDataAccessConfiguration config) {

        return new File(config.getBasePath(), config.getName());
    }
}
