package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

public class GitContainer<Id> {


    private final GitDataAccessConfiguration config;

    private final String catalogType;

    private final Function<Id, String> idToContainerMap;

    public GitContainer(final GitDataAccessConfiguration config, final String catalogType, final Function<Id, String> idToContainerMap) {
        this.config = config;
        this.catalogType = catalogType;
        this.idToContainerMap = idToContainerMap;
    }

    public File getWorkingDir() {

        return new File(config.getBasePath(), config.getName());
    }


    public File get(final Id id, final boolean createIfNotExist)
            throws DataAccessWriterException {

        File catalog = getCatalog();

        File objectContainer = new File(catalog, this.idToContainerMap.apply(id));
        if (!objectContainer.exists()) {
            if (createIfNotExist) {
                boolean created = objectContainer.mkdir();
                if (!created) {
                    throw new DataAccessWriterException("Unable to create dir: " + objectContainer.getAbsolutePath());
                }
            }
        }

        return objectContainer;
    }

    public String getGitRepo() {
        return this.config.getGitRepo();
    }

    public String getCommitMessage() {
        return this.config.getCommitMessage();
    }

    public String getCommitUser() {
        return this.config.getCommitUser();
    }

    public String getCommitEmail() {
        return this.config.getCommitEmail();
    }

    public String getUsername() {
        return this.config.getUsername();
    }

    public String getPassword() {
        return this.config.getPassword();
    }

    public boolean hasObject(final Id id) {

        this.ensureUpdatedAsync();

        try {
            File objectContainer = this.get(id, false);
            return objectContainer.exists();
        }
        catch (DataAccessWriterException e) {
            return false;
        }
    }

    protected final void ensureUpdated() {

        final File workingDir = getWorkingDir();
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

            try {
                Git.open(workingDir)
                   .pull()
                   .call();
            }
            catch (GitAPIException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public final void ensureUpdatedAsync() {
        new Thread(this::ensureUpdated).start();
    }

    protected File getCatalog() throws DataAccessWriterException {


        File catalog = new File(getWorkingDir(), catalogType);
        if (!catalog.exists()) {

            if (!catalog.getParentFile().exists()) {
                catalog.getParentFile().mkdirs();
            }

            this.ensureUpdated();
            if (!catalog.exists()) {
                boolean created = catalog.mkdir();
                if (!created) {
                    throw new DataAccessWriterException("Unable to create dir: " + catalog.getAbsolutePath());
                }
            }
        }

        return catalog;
    }
}
