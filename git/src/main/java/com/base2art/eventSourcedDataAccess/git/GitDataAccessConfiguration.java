package com.base2art.eventSourcedDataAccess.git;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class GitDataAccessConfiguration {

    @NonNull
    private final String basePath;

    @NonNull
    private final String gitRepo;

    @NonNull
    private final String username;

    @NonNull
    private final String password;

    @NonNull
    private final String name;

    @NonNull
    private final String commitUser;

    @NonNull
    private final String commitEmail;

    @NonNull
    private final String commitMessage;

    private final boolean isLocal;
}
