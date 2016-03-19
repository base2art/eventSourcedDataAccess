package com.base2art.eventSourcedDataAccess.git;

import com.base2art.eventSourcedDataAccess.DataAccessReader;
import com.base2art.eventSourcedDataAccess.DataAccessWriter;
import com.base2art.eventSourcedDataAccess.FilteredDataAccessReader;
import com.base2art.eventSourcedDataAccess.FilteredPagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.ItemDataAccessReader;
import com.base2art.eventSourcedDataAccess.PagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.git.impls.GitPojoDataAccessReaderImpl;
import com.base2art.eventSourcedDataAccess.git.impls.GitPojoDataAccessWriterImpl;
import com.base2art.eventSourcedDataAccess.git.utils.IOUtils;
import com.base2art.eventSourcedDataAccess.testing.pojo.PojoAccessorFactory;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

public class GitPojoAccessorFactory implements PojoAccessorFactory {


    private final GitDataAccessConfiguration config;
    private final String catalogType;
    private final File base;
    private final File next;

    public GitPojoAccessorFactory() {

        try {
            File temp = File.createTempFile("temp-file-name", ".tmp");
            temp.deleteOnExit();

            File f_base = new File(temp.getParentFile(), "existingGitRepo");
            File f_next = new File(temp.getParentFile(), "newRepo");
            f_base.mkdirs();
            f_next.mkdirs();

            this.base = new File(f_base, UUID.randomUUID().toString());
            this.next = new File(f_next, UUID.randomUUID().toString());
            base.mkdir();

            System.out.println("pre-init");
            Git.init()
               .setDirectory(base)
               .call();

            System.out.println("post-init");

            Files.write(new File(base, "abc").toPath(), Collections.singletonList(""));


            System.out.println("pre-add");
            Git.open(base)
               .add()
               .addFilepattern(".")
               .call();
            System.out.println("post-add");

            System.out.println("pre-commit");
            Git.open(base)
               .commit()
               .setAuthor("abc", "other")
               .setCommitter("abc", "other")
               .setMessage("other")
               .call();

            System.out.println("post-commit");
        }
        catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }

        this.config = GitDataAccessConfiguration.of(
                next.getAbsolutePath(),
                "file://" + base.getAbsolutePath(),
                "",
                "",
                "name",
                "sjY",
                "SjY@gmail.com",
                "Message",
                true);
        this.catalogType = new Random().nextInt() + "";


        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        IOUtils.deleteRecursive(this.next);
        IOUtils.deleteRecursive(this.base);
    }

    @Override
    public DataAccessWriter<UUID, PersonData, PersonVersionData> writer() {

        return new GitPojoDataAccessWriterImpl(config, catalogType);
    }

    @Override
    public ItemDataAccessReader<UUID, Person> itemReader() {

        return new GitPojoDataAccessReaderImpl(config, catalogType);
    }

    @Override
    public DataAccessReader<Person> setReader() {
        return new GitPojoDataAccessReaderImpl(config, catalogType);
    }

    @Override
    public FilteredDataAccessReader<Person, PersonFilterOptions> filteredSetReader() {
        return new GitPojoDataAccessReaderImpl(config, catalogType);
    }

    @Override
    public FilteredPagedDataAccessReader<UUID, Person, PersonFilterOptions, PersonOrderOptions> filteredPagedSetReader() {
        return new GitPojoDataAccessReaderImpl(config, catalogType);
    }

    @Override
    public PagedDataAccessReader<UUID, Person, PersonOrderOptions> pagedSetReader() {
        return new GitPojoDataAccessReaderImpl(config, catalogType);
    }

    public void ensureUpdated() {

        GitPojoDataAccessWriterImpl impl = new GitPojoDataAccessWriterImpl(config, catalogType);
        impl.ensureUpdated();
    }
}
