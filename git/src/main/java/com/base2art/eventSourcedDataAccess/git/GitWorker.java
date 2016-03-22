package com.base2art.eventSourcedDataAccess.git;

import lombok.Getter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.base2art.eventSourcedDataAccess.git.Util.getWorkingDir;

public class GitWorker {

    @Getter
    private final GitDataAccessConfiguration config;

    public GitWorker(GitDataAccessConfiguration config) {
        this.config = config;
    }

//    try {
//        commitFiles();
//    }
//    catch (IOException | URISyntaxException | GitAPIException e) {
//        throw new RuntimeException(e);
//    }

    public void commit() throws IOException, URISyntaxException, GitAPIException {

        final File workingDir = getWorkingDir(this.config);
        final RevCommit commit;
        try (Git open = Git.open(workingDir)) {

            open.add()
                .addFilepattern(".")
                .call();

            commit = open
                    .commit()
                    .setMessage(this.config.getCommitMessage())
                    .setAuthor(this.config.getCommitUser(), this.config.getCommitEmail())
                    .call();
        }

        if (this.config.isDebug()) {
            System.out.println("commit " + commit);
        }
    }

    public void push() throws IOException, URISyntaxException, GitAPIException {

        final File workingDir = getWorkingDir(config);
        final Iterable<PushResult> pushResult;
        try (Git open = Git.open(workingDir)) {

            final StoredConfig repoConfig = open.getRepository().getConfig();
            RemoteConfig remoteConfig = new RemoteConfig(repoConfig, "gh-enterprise-gd");
            URIish uri = new URIish(this.config.getGitRepo());
            remoteConfig.addURI(uri);
            remoteConfig.update(repoConfig);
            repoConfig.save();

            pushResult = open
                    .push()
                    .setRemote("gh-enterprise-gd")
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.config.getUsername(), this.config.getPassword()))
                    .call();
        }

        if (this.config.isDebug()) {
            System.out.println("push " + pushResult);

            pushResult.forEach(x -> System.out.println(x.getMessages()));
        }
    }

    protected final void pull() {

        final File workingDir = getWorkingDir(config);
        if (!workingDir.exists()) {

            try (Git cloneResult = Git.cloneRepository()
                                      .setURI(config.getGitRepo())
                                      .setDirectory(workingDir)
                                      .setCloneAllBranches(true)
                                      .setCredentialsProvider(new UsernamePasswordCredentialsProvider(config.getUsername(), config.getPassword()))
                                      .call()) {
            }
            catch (GitAPIException e) {
                throw new RuntimeException(e);
            }
        }

        if (!config.isLocal()) {

            try (final Git open = Git.open(workingDir)) {
                open
                        .pull()
                        .call();
            }
            catch (GitAPIException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
