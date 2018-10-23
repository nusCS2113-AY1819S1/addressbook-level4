package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.EventListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.EventList;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;


public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setup() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        XmlEventStorage eventStorage = new XmlEventStorage(getTempFilePath("el"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, eventStorage, userPrefsStorage);
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

    // ================ AddressBook tests ==============================

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
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }


    /**
     * A Stub class to throw an exception when the save method is called for addressbook
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

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlEventStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    // ================ EventList tests ==============================
    @Test
    public void eventListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlEventListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlEventListStorageTest} class.
         */
        EventList original = getTypicalEventList();
        storageManager.saveEventList(original);
        ReadOnlyEventList retrieved = storageManager.readEventList().get();
        assertEquals(original, new EventList(retrieved));
    }

    @Test
    public void getEventListFilePath() {
        assertNotNull(storageManager.getEventListFilePath());
    }


    /**
     * A Stub class to throw an exception when the save method is called for event
     */
    class XmlEventStorageExceptionThrowingStub extends XmlEventStorage {

        public XmlEventStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveEventList(ReadOnlyEventList eventList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    @Test
    public void handleEventListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlEventStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleEventListChangedEvent(new EventListChangedEvent(new EventList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

}
