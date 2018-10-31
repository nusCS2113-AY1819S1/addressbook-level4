package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.ExpenseBookChangedEvent;
import seedu.address.commons.events.model.UserPrefsChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.ReadOnlyExpenseBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;
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
        XmlEventBookStorage eventBookStorage = new XmlEventBookStorage(getTempFilePath("ev"));
        XmlExpenseBookStorage expenseBookStorage = new XmlExpenseBookStorage(getTempFilePath("eb"));
        XmlTaskBookStorage taskBookStorage = new XmlTaskBookStorage(getTempFilePath("tb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, expenseBookStorage, eventBookStorage,
                taskBookStorage, userPrefsStorage);
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

    //@@author QzSG
    @Test
    public void addressBookReadBackup() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of AddressBook saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original, getTempFilePath("AddressBook.bak"));
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(getTempFilePath("AddressBook.bak")).get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void expenseBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlExpenseBookStorage} class.
         * More extensive testing of ExpenseBook saving/reading is done in {@link XmlExpenseBookStorageTest} class.
         */
        ExpenseBook original = getTypicalExpenseBook();
        storageManager.saveExpenseBook(original);
        ReadOnlyExpenseBook retrieved = storageManager.readExpenseBook().get();
        assertEquals(original.hashCode(), new ExpenseBook(retrieved).hashCode());
    }

    @Test
    public void expenseBookReadBackup() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlExpenseBookStorage} class.
         * More extensive testing of ExpenseBook saving/reading is done in {@link XmlExpenseBookStorageTest} class.
         */
        ExpenseBook original = getTypicalExpenseBook();
        storageManager.backupExpenseBook(original, getTempFilePath("ExpenseBook.bak"));
        ReadOnlyExpenseBook retrieved = storageManager.readExpenseBook(getTempFilePath("ExpenseBook.bak")).get();
        assertEquals(original.hashCode(), new ExpenseBook(retrieved).hashCode());
    }

    //@@author
    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlExpenseBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlEventBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTaskBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));

        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    //@@author QzSG
    @Test
    public void handleExpenseBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlExpenseBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlEventBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTaskBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorageExceptionThrowingStub(Paths.get("dummy")));
        storage.handleExpenseBookChangedEvent(new ExpenseBookChangedEvent(new ExpenseBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleUserPrefsChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlExpenseBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlEventBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTaskBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorageExceptionThrowingStub(Paths.get("dummy")));
        storage.handleUserPrefsChangedEvent(new UserPrefsChangedEvent(new UserPrefs()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class JsonUserPrefsStorageExceptionThrowingStub extends JsonUserPrefsStorage {

        public JsonUserPrefsStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    //@@author

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
    class XmlExpenseBookStorageExceptionThrowingStub extends XmlExpenseBookStorage {

        public XmlExpenseBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveExpenseBook(ReadOnlyExpenseBook expenseBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlEventBookStorageExceptionThrowingStub extends XmlEventBookStorage {

        public XmlEventBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveEventBook(ReadOnlyEventBook eventBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    class XmlTaskBookStorageExceptionThrowingStub extends XmlTaskBookStorage {

        public XmlTaskBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskBook(ReadOnlyTaskBook taskBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
