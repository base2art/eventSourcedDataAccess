//package com.base2art.eventSourcedDataAccess.testing;
//
//import com.base2art.eventSourcedDataAccess.DataAccessReader;
//import com.base2art.eventSourcedDataAccess.DataAccessReaderException;
//import com.base2art.eventSourcedDataAccess.DataAccessWriter;
//import com.base2art.eventSourcedDataAccess.DataAccessWriterException;
//import com.base2art.eventSourcedDataAccess.FilteredDataAccessReader;
//import com.base2art.eventSourcedDataAccess.FilteredPagedDataAccessReader;
//import com.base2art.eventSourcedDataAccess.ItemDataAccessReader;
//import com.base2art.eventSourcedDataAccess.PagedDataAccessReader;
//import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.Person;
//import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonData;
//import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonFilterOptions;
//import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonOrderOptions;
//import com.base2art.eventSourcedDataAccess.testing.pojo.fixtures.PersonVersionData;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public abstract class TestBase {
//
//
//    private final List<PersonData> expectedPerson = new ArrayList<>();
//    private final List<UUID> expectedIDs = new ArrayList<>();
//    private final List<PersonVersionData> expectedVersion = new ArrayList<>();
//
//
//    @Before
//    public void beforeEach() {
//        this.reset();
//        this.expectedPerson.clear();
//        this.expectedIDs.clear();
//        this.expectedVersion.clear();
//    }
//
//    protected void reset() {
//    }
//
//    @Test
//    public void canReadSingleItem() throws DataAccessReaderException, DataAccessWriterException {
//
//        ItemDataAccessReader<UUID, Person> getter = this.itemReader();
//        DataAccessWriter<UUID, PersonData, PersonVersionData> writer = this.writer();
//
//        PersonData pd = new PersonData();
//        pd.setSocialSecurityNumber("123-00-7897");
//        PersonVersionData pvd = new PersonVersionData();
//        pvd.setName("SjY");
//
//        UUID id = UUID.randomUUID();
//        writer.createObject(id, pd);
//        writer.insertVersion(id, pvd);
//
//        Person actual = getter.getItem(id);
//
//        assertThat(actual.getName()).isEqualTo("SjY");
//        assertThat(actual.getSocialSecurityNumber()).isEqualTo("123-00-7897");
//    }
//
//    @Test
//    public void canReadManyItems() throws DataAccessReaderException, DataAccessWriterException {
//
//        DataAccessReader<Person> getter = this.setReader();
//        DataAccessWriter<UUID, PersonData, PersonVersionData> writer = this.writer();
//
//        PersonData pd = new PersonData();
//        pd.setSocialSecurityNumber("123-00-7897");
//        PersonVersionData pvd = new PersonVersionData();
//        pvd.setName("SjY");
//
//        UUID id = UUID.randomUUID();
//        writer.createObject(id, pd);
//        writer.insertVersion(id, pvd);
//
//        Person[] actuals = getter.get();
//
//        assertThat(actuals.length).isEqualTo(1);
//
//        Person actual = actuals[0];
//        assertThat(actual.getName()).isEqualTo("SjY");
//        assertThat(actual.getSocialSecurityNumber()).isEqualTo("123-00-7897");
//    }
//
//    @Test
//    public void canReadManyItemsFiltered() throws DataAccessReaderException, DataAccessWriterException {
//
//        FilteredDataAccessReader<Person, PersonFilterOptions> getter = this.filteredSetReader();
//        DataAccessWriter<UUID, PersonData, PersonVersionData> writer = this.writer();
//
//        PersonData pd = new PersonData();
//        pd.setSocialSecurityNumber("123-00-7897");
//        PersonVersionData pvd = new PersonVersionData();
//        pvd.setName("SjY");
//
//        UUID id = UUID.randomUUID();
//        writer.createObject(id, pd);
//        writer.insertVersion(id, pvd);
//
//        Person[] actuals = getter.getFiltered(new PersonFilterOptions());
//
//        assertThat(actuals.length).isEqualTo(1);
//
//        Person actual = actuals[0];
//        assertThat(actual.getName()).isEqualTo("SjY");
//        assertThat(actual.getSocialSecurityNumber()).isEqualTo("123-00-7897");
//    }
//
//    @Test
//    public void canReadManyItemsPaged() throws DataAccessReaderException, DataAccessWriterException {
//
//        PagedDataAccessReader<UUID, Person, PersonOrderOptions> getter = this.pagedSetReader();
//        DataAccessWriter<UUID, PersonData, PersonVersionData> writer = this.writer();
//
//        SetupData(writer, expectedPerson, expectedVersion, expectedIDs, 1000);
//
//        final Person[] paged = getter.getPaged(
//                new PersonOrderOptions(), null, 20);
//        assertForNoMarker(paged, 20, "SjY1000", "SjY1019");
//
//
//        final Person[] pagedPage1 = getter.getPaged(
//                new PersonOrderOptions(), expectedIDs.get(19), 20);
//        assertForNoMarker(pagedPage1, 20, "SjY1020", "SjY1039");
//    }
//
//    @Test
//    public void canReadManyItemsFilteredAndPaged() throws DataAccessReaderException, DataAccessWriterException {
//
//        FilteredPagedDataAccessReader<UUID, Person, PersonFilterOptions, PersonOrderOptions> getter = this.filteredPagedSetReader();
//        DataAccessWriter<UUID, PersonData, PersonVersionData> writer = this.writer();
//
//
//        SetupData(writer, expectedPerson, expectedVersion, expectedIDs, 1000);
//
//        final Person[] filteredAndPaged = getter.getFilteredAndPaged(
//                new PersonFilterOptions(), new PersonOrderOptions(), null, 20);
//        assertForNoMarker(filteredAndPaged, 20, "SjY1000", "SjY1019");
//
//
//        final Person[] filteredAndPagedPage1 = getter.getFilteredAndPaged(
//                new PersonFilterOptions(), new PersonOrderOptions(), expectedIDs.get(19), 20);
//        assertForNoMarker(filteredAndPagedPage1, 20, "SjY1020", "SjY1039");
//    }
//
//    private void SetupData(
//            final DataAccessWriter<UUID, PersonData, PersonVersionData> writer,
//            final List<PersonData> expectedPerson,
//            final List<PersonVersionData> expectedVersion,
//            final List<UUID> expectedIDs,
//            final int count) throws DataAccessWriterException {
//        for (int i = 1000; i < 1000 + count; i++) {
//
//            PersonData pd = new PersonData();
//            pd.setSocialSecurityNumber("123-00-" + i);
//            PersonVersionData pvd = new PersonVersionData();
//            pvd.setName("SjY" + i);
//
//            UUID id = UUID.randomUUID();
//            writer.createObject(id, pd);
//            writer.insertVersion(id, pvd);
//
//            expectedPerson.add(pd);
//            expectedVersion.add(pvd);
//            expectedIDs.add(id);
//        }
//    }
//
//    private void assertForNoMarker(
//            final Person[] actuals,
//            final int pageSize,
//            final String firstItemName,
//            final String lastItemName) {
//
//        assertThat(actuals.length).isEqualTo(pageSize);
//
//        assertThat(actuals[0].getName()).isEqualTo(firstItemName);
//        assertThat(actuals[actuals.length - 1].getName()).isEqualTo(lastItemName);
//    }
//
//
//    protected abstract FilteredDataAccessReader<Person, PersonFilterOptions> filteredSetReader();
//
//    protected abstract PagedDataAccessReader<UUID, Person, PersonOrderOptions> pagedSetReader();
//
//    protected abstract FilteredPagedDataAccessReader<UUID, Person, PersonFilterOptions, PersonOrderOptions> filteredPagedSetReader();
//
//    protected abstract DataAccessWriter<UUID, PersonData, PersonVersionData> writer();
//
//    protected abstract ItemDataAccessReader<UUID, Person> itemReader();
//
//    protected abstract DataAccessReader<Person> setReader();
//}
