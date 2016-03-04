package com.base2art.eventSourcedDataAccess.testing.pojo;

import com.base2art.eventSourcedDataAccess.DataAccessReader;
import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
import com.base2art.eventSourcedDataAccess.DataAccessWriter;
import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
import com.base2art.eventSourcedDataAccess.FilteredDataAccessReader;
import com.base2art.eventSourcedDataAccess.FilteredPagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.ItemDataAccessReader;
import com.base2art.eventSourcedDataAccess.PagedDataAccessReader;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
import com.base2art.eventSourcedDataAccess.testing.utils.ResultableService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class PojoTestBase {


    private PojoAccessorFactory pojoAccessFactory;

    private final List<PersonData> expectedPerson = new ArrayList<>();
    private final List<UUID> expectedIDs = new ArrayList<>();
    private final List<PersonVersionData> expectedVersion = new ArrayList<>();


    @Before
    public void beforeEach() {
        this.resetData();
    }

    @After
    public void afterEach() {
        this.cleanData();
        this.expectedIDs.clear();
        this.expectedPerson.clear();
        this.expectedVersion.clear();
    }

    @Test
    public void canReadSingleItem() throws DataAccessReaderException, DataAccessWriterException {

        ResultableService<ItemDataAccessReader<UUID, Person>> getter = this.tryGet(this::itemReader);

        org.junit.Assume.assumeTrue(!getter.hasError());

        DataAccessWriter<UUID, PersonData, PersonVersionData> writer = this.writer();

        PersonData pd = new PersonData();
        pd.setSocialSecurityNumber("123-00-7897");
        PersonVersionData pvd1 = new PersonVersionData();
        PersonVersionData pvd2 = new PersonVersionData();
        pvd1.setName("MjY");

        UUID id = UUID.randomUUID();
        writer.createObject(id, pd);
        writer.insertVersion(id, pvd1);

        pvd2.setName("SjY");
        writer.insertVersion(id, pvd2);


        Person actual = getter.getService().getItem(id);

        assertThat(actual.getName()).isEqualTo("SjY");
        assertThat(actual.getSocialSecurityNumber()).isEqualTo("123-00-7897");
    }

    @Test
    public void canReadManyItems() throws DataAccessReaderException, DataAccessWriterException {

        ResultableService<DataAccessReader<Person>> getter = this.tryGet(this::setReader);

        org.junit.Assume.assumeTrue(!getter.hasError());

        DataAccessWriter<UUID, PersonData, PersonVersionData> writer = this.writer();

        PersonData pd = new PersonData();
        pd.setSocialSecurityNumber("123-00-7897");
        PersonVersionData pvd = new PersonVersionData();
        pvd.setName("SjY");

        UUID id = UUID.randomUUID();
        writer.createObject(id, pd);
        writer.insertVersion(id, pvd);

        Person[] actuals = getter.getService().get();

        assertThat(actuals.length).isEqualTo(1);

        Person actual = actuals[0];
        assertThat(actual.getName()).isEqualTo("SjY");
        assertThat(actual.getSocialSecurityNumber()).isEqualTo("123-00-7897");
    }

    @Test
    public void canReadManyItemsFiltered() throws DataAccessReaderException, DataAccessWriterException {

        ResultableService<FilteredDataAccessReader<Person, PersonFilterOptions>> getter = this.tryGet(this::filteredSetReader);

        org.junit.Assume.assumeTrue(!getter.hasError());

        setupData(expectedPerson, expectedVersion, expectedIDs, 250);

        final Person[] filteredAndPaged = getter.getService().getFiltered(
                new PersonFilterOptions(new EndsWithFilter("9")));
        Arrays.sort(filteredAndPaged, (x, y) -> x.getName().compareTo(y.getName()));
        assertForNoMarker(filteredAndPaged, 25, "SjY1009", "SjY1249");
    }

    @Test
    public void canReadManyItemsPaged() throws DataAccessReaderException, DataAccessWriterException {

        ResultableService<PagedDataAccessReader<UUID, Person, PersonOrderOptions>> getter = this.tryGet(this::pagedSetReader);

        org.junit.Assume.assumeTrue(!getter.hasError());

        setupData(expectedPerson, expectedVersion, expectedIDs, 25);

        final Person[] filteredAndPaged = getter.getService().getPaged(
                PersonOrderOptions.NameAsc, null, 10);
        assertForNoMarker(filteredAndPaged, 10, "SjY1000", "SjY1009");

        final Person[] filteredAndPagedPage1 = getter.getService().getPaged(
                PersonOrderOptions.NameAsc, lastId(filteredAndPaged), 10);

        assertForNoMarker(filteredAndPagedPage1, 10, "SjY1010", "SjY1019");
    }

    @Test
    public void canReadManyItemsFilteredAndPaged() throws DataAccessReaderException, DataAccessWriterException {

        ResultableService<FilteredPagedDataAccessReader<UUID, Person, PersonFilterOptions, PersonOrderOptions>> getter
                = tryGet(this::filteredPagedSetReader);

        org.junit.Assume.assumeTrue(!getter.hasError());

        setupData(expectedPerson, expectedVersion, expectedIDs, 25);

        final Person[] filteredAndPaged = getter.getService().getFilteredAndPaged(
                new PersonFilterOptions(), PersonOrderOptions.NameAsc, null, 10);
        assertForNoMarker(filteredAndPaged, 10, "SjY1000", "SjY1009");

        final Person[] filteredAndPagedPage1 = getter.getService().getFilteredAndPaged(
                new PersonFilterOptions(),
                PersonOrderOptions.NameAsc,
                filteredAndPaged[filteredAndPaged.length - 1].getId(),
                10);

        assertForNoMarker(filteredAndPagedPage1, 10, "SjY1010", "SjY1019");
    }

    protected abstract PojoAccessorFactory get();

    protected void resetData() {
        this.pojoAccessFactory = this.get();
    }


    protected void cleanData() {
        if (this.pojoAccessFactory != null) {
            this.pojoAccessFactory.destroy();
        }
    }

    protected DataAccessWriter<UUID, PersonData, PersonVersionData> writer() {

        return this.pojoAccessFactory.writer();
    }

    protected ItemDataAccessReader<UUID, Person> itemReader() {

        return this.pojoAccessFactory.itemReader();
    }

    protected DataAccessReader<Person> setReader() {

        return this.pojoAccessFactory.setReader();
    }

    protected FilteredDataAccessReader<Person, PersonFilterOptions> filteredSetReader() {

        return this.pojoAccessFactory.filteredSetReader();
    }

    protected PagedDataAccessReader<UUID, Person, PersonOrderOptions> pagedSetReader() {

        return this.pojoAccessFactory.pagedSetReader();
    }

    protected FilteredPagedDataAccessReader<UUID, Person, PersonFilterOptions, PersonOrderOptions> filteredPagedSetReader() {

        return this.pojoAccessFactory.filteredPagedSetReader();
    }

    private void setupData(
            final List<PersonData> expectedPerson,
            final List<PersonVersionData> expectedVersion,
            final List<UUID> expectedIDs,
            final int count) throws DataAccessWriterException {
        for (int i = 1000; i < 1000 + count; i++) {

            PersonData pd = new PersonData();
            pd.setSocialSecurityNumber("123-00-" + i);
            PersonVersionData pvd = new PersonVersionData();
            pvd.setName("SjY" + i);

            UUID id = UUID.randomUUID();
            this.writer().createObject(id, pd);
            this.writer().insertVersion(id, pvd);

            expectedPerson.add(pd);
            expectedVersion.add(pvd);
            expectedIDs.add(id);
        }
    }

    private void assertForNoMarker(
            final Person[] actuals,
            final int pageSize,
            final String firstItemName,
            final String lastItemName) {

        assertThat(actuals.length).isEqualTo(pageSize);

        assertThat(actuals[0].getName()).isEqualTo(firstItemName);
        assertThat(actuals[actuals.length - 1].getName()).isEqualTo(lastItemName);
    }

    private UUID lastId(final Person[] filteredAndPaged) {
        return filteredAndPaged[filteredAndPaged.length - 1].getId();
    }

    private <T> ResultableService<T> tryGet(final Supplier<T> supplier) {

        try {
            return new ResultableService<>(supplier.get(), false);
        }
        catch (NotImplementedException nie) {
            return new ResultableService<>(null, true);
        }
    }
}
