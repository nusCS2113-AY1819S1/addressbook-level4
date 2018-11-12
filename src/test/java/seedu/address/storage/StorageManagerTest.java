package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ExpensesListChangedEvent;
import seedu.address.commons.events.model.RecruitmentListChangedEvent;
import seedu.address.commons.events.model.ScheduleListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.UserPrefs;
import seedu.address.model.addressbook.AddressBook;
import seedu.address.model.addressbook.ReadOnlyAddressBook;
import seedu.address.model.expenses.ExpensesList;
import seedu.address.model.expenses.ReadOnlyExpensesList;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.storage.addressbook.XmlAddressBookStorage;
import seedu.address.storage.expenses.XmlExpensesListStorage;
import seedu.address.storage.recruitment.XmlRecruitmentListStorage;
import seedu.address.storage.schedule.XmlScheduleListStorage;
import seedu.address.storage.userpref.JsonUserPrefsStorage;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        XmlExpensesListStorage expensesListStorage = new XmlExpensesListStorage(getTempFilePath("el"));
        XmlScheduleListStorage scheduleListStorage = new XmlScheduleListStorage(getTempFilePath("ab"));
        XmlRecruitmentListStorage recruitmentListStorage = new XmlRecruitmentListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, expensesListStorage, scheduleListStorage,
                 recruitmentListStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void scheduleListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlScheduleListStorageTest} class.
         */
        ScheduleList original = getTypicalScheduleList();
        storageManager.saveScheduleList(original);
        ReadOnlyScheduleList retrieved = storageManager.readScheduleList().get();
        assertEquals(original, new ScheduleList(retrieved));
    }

    @Test
    public void expensesListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlExpensesListStorageTest} class.
         */
        ExpensesList original = getTypicalExpensesList();
        storageManager.saveExpensesList(original);
        ReadOnlyExpensesList retrieved = storageManager.readExpensesList().get();
        assertEquals(original, new ExpensesList(retrieved));
    }

    @Test
    public void recruitmentListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlRecruitmentListStorageTest} class.
         */
        RecruitmentList original = getTypicalRecruitmentList();
        storageManager.saveRecruitmentList(original);
        ReadOnlyRecruitmentList retrieved = storageManager.readRecruitmentList().get();
        assertEquals(original, new RecruitmentList(retrieved));
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void getScheduleListFilePath() {
        assertNotNull(storageManager.getScheduleListFilePath());
    }

    @Test
    public void getExpensesListFilePath() {
        assertNotNull(storageManager.getExpensesListFilePath());
    }

    @Test
    public void getRecruitmentListFilePath() {
        assertNotNull(storageManager.getRecruitmentListFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")), (
                new XmlExpensesListStorageExceptionThrowingStub(Paths.get("dummy"))), (
                new XmlScheduleListStorageExceptionThrowingStub(Paths.get("dummy"))),
                new XmlRecruitmentListStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleScheduleListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")), (
                new XmlExpensesListStorageExceptionThrowingStub(Paths.get("dummy"))), (
                new XmlScheduleListStorageExceptionThrowingStub(Paths.get("dummy"))), (
                new XmlRecruitmentListStorageExceptionThrowingStub(Paths.get("dummy"))),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleScheduleListChangedEvent(new ScheduleListChangedEvent(new ScheduleList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleRecruitmentListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")), (
                new XmlExpensesListStorageExceptionThrowingStub(Paths.get("dummy"))), (
                new XmlScheduleListStorageExceptionThrowingStub(Paths.get("dummy"))), (
                new XmlRecruitmentListStorageExceptionThrowingStub(Paths.get("dummy"))),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleRecruitmentListChangedEvent(new RecruitmentListChangedEvent(new RecruitmentList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleExpensesListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")), (
                new XmlExpensesListStorageExceptionThrowingStub(Paths.get("dummy"))), (
                new XmlScheduleListStorageExceptionThrowingStub(Paths.get("dummy"))), (
                new XmlRecruitmentListStorageExceptionThrowingStub(Paths.get("dummy"))),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleExpensesListChangedEvent(new ExpensesListChangedEvent(new ExpensesList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlExpensesListStorageExceptionThrowingStub extends XmlExpensesListStorage {

        public XmlExpensesListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveExpensesList(ReadOnlyExpensesList expensesList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlScheduleListStorageExceptionThrowingStub extends XmlScheduleListStorage {

        public XmlScheduleListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveScheduleList(ReadOnlyScheduleList scheduleList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlRecruitmentListStorageExceptionThrowingStub extends XmlRecruitmentListStorage {

        public XmlRecruitmentListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveRecruitmentList(ReadOnlyRecruitmentList recruitmentList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
