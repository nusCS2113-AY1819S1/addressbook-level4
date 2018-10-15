package UnRefactored.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static UnRefactored.testutil.TypicalTasks.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import UnRefactored.commons.events.model.TaskBookChangedEvent;
import UnRefactored.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyTaskBook;
import seedu.address.model.UserPrefs;
import UnRefactored.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlTaskBookStorage addressBookStorage = new XmlTaskBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
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
         * {@link XmlTaskBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTaskBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveTaskBook(original);
        ReadOnlyTaskBook retrieved = storageManager.readTaskBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getTaskBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlTaskBookStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleAddressBookChangedEvent(new TaskBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTaskBookStorageExceptionThrowingStub extends XmlTaskBookStorage {

        public XmlTaskBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTaskBook(ReadOnlyTaskBook addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
