package seedu.divelog.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.divelog.testutil.TypicalDiveSessions.getTypicalDiveLog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.divelog.commons.events.model.DiveLogChangedEvent;
import seedu.divelog.commons.events.storage.DataSavingExceptionEvent;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.ReadOnlyDiveLog;
import seedu.divelog.model.UserPrefs;
import seedu.divelog.testutil.EventsCollectorRule;


public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlDiveLogStorage diveLogStorage = new XmlDiveLogStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(diveLogStorage, userPrefsStorage);
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
    public void diveLogReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlDiveLogStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlDiveLogStorageTest} class.
         */
        DiveLog original = getTypicalDiveLog();
        storageManager.saveDiveLog(original);
        ReadOnlyDiveLog retrieved = storageManager.readDiveLog().get();
        assertEquals(original, new DiveLog(retrieved));
    }

    @Test
    public void getDiveLogFilePath() {
        assertNotNull(storageManager.getDiveLogFilePath());
    }

    @Test
    public void handleDiveLogChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlDiveLogStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleDiveLogChangedEvent(new DiveLogChangedEvent(new DiveLog()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlDiveLogStorageExceptionThrowingStub extends XmlDiveLogStorage {

        public XmlDiveLogStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveDiveLog(ReadOnlyDiveLog diveLog, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
