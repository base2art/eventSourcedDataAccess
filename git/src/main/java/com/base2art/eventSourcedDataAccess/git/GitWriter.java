package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class GitWriter<Id> {

    private final GitContainer<Id> container;
    private final ObjectMapper mapper;
    private boolean isDebug;

    public GitWriter(final GitContainer<Id> container, ObjectMapper mapper, boolean isDebug) {
        this.container = container;
        this.mapper = mapper;
        this.isDebug = isDebug;
    }


    public final <T> void writeObject(final Id id, final String fileName, T objectToSerialize)
            throws DataAccessWriterException {

        File objectContainer = container.get(id, true);

        File objectData = new File(objectContainer, fileName);
        if (objectData.exists()) {
            throw new DataAccessWriterException("Object already exists: " + objectData.getAbsolutePath());
        }

        writeAndCommit(objectToSerialize, objectData);


        new Thread(() -> {
            try {
                pushBack();
            }
            catch (IOException | URISyntaxException | GitAPIException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void ensureUpdated() {
        this.container.ensureUpdated();
    }

    public void ensureUpdatedAsync() {
        this.container.ensureUpdatedAsync();
    }

    public boolean hasObject(final Id id) {
        return this.container.hasObject(id);
    }

    private synchronized <T> void writeAndCommit(final T objectToSerialize, final File objectData)
            throws DataAccessWriterException {

        try {
            mapper.writeValue(objectData, objectToSerialize);
        }
        catch (IOException e) {
            throw new DataAccessWriterException(e);
        }

        try {
            commit();
        }
        catch (IOException | URISyntaxException | GitAPIException e) {
            throw new RuntimeException(e);
        }
    }


    private void commit() throws IOException, URISyntaxException, GitAPIException {

        final File workingDir = this.container.getWorkingDir();
        final Git open = Git.open(workingDir);


        open.add()
            .addFilepattern(".")
            .call();

        RevCommit commit = open
                .commit()
                .setMessage(this.container.getCommitMessage())
                .setAuthor(this.container.getCommitUser(), this.container.getCommitEmail())
                .call();

        if (this.isDebug) {
            System.out.println("commit " + commit);
        }
    }

    private void pushBack() throws IOException, URISyntaxException, GitAPIException {

        final File workingDir = this.container.getWorkingDir();
        final Git open = Git.open(workingDir);

        final StoredConfig repoConfig = open.getRepository().getConfig();
        RemoteConfig remoteConfig = new RemoteConfig(repoConfig, "gh-enterprise-gd");
        URIish uri = new URIish(this.container.getGitRepo());
        remoteConfig.addURI(uri);
        remoteConfig.update(repoConfig);
        repoConfig.save();


        Iterable<PushResult> pushResult = open
                .push()
                .setRemote("gh-enterprise-gd")
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(this.container.getUsername(), this.container.getPassword()))
                .call();
        if (this.isDebug) {
            System.out.println("push " + pushResult);

            pushResult.forEach(x -> System.out.println(x.getMessages()));
        }
    }
}
