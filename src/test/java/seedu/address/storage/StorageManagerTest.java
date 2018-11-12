package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalTrackedData;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.TrackedDataChangedEvent;
import seedu.address.commons.events.model.TrackedDataListChangedEvent;
import seedu.address.commons.events.model.WorkoutBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlyTrackedData;
import seedu.address.model.ReadOnlyTrackedDataList;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.TrackedData;
import seedu.address.model.TrackedDataList;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutBook;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlWorkoutBookStorage workoutBookStorage = new XmlWorkoutBookStorage(getTempFilePath("wb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        XmlTrackedDataListStorage trackedDataListStorage = new XmlTrackedDataListStorage(getTempFilePath("tdl"));
        XmlTrackedDataStorage trackedDataStorage = new XmlTrackedDataStorage(getTempFilePath("td"));
        storageManager = new StorageManager(workoutBookStorage, trackedDataListStorage, trackedDataStorage,
                userPrefsStorage);
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
    public void workoutBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlWorkoutBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlWorkoutBookStorageTest} class.
         */
        WorkoutBook original = getTypicalWorkoutBook();
        storageManager.saveWorkoutBook(original);
        ReadOnlyWorkoutBook retrieved = storageManager.readWorkoutBook().get();
        assertEquals(original, new WorkoutBook(retrieved));
    }

    @Test
    public void trackedDataListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTrackedDataListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTrackedDataListStorage} class.
         */
        TrackedDataList original = getTypicalTrackedDataList();
        storageManager.saveTrackedDataList(original);
        ReadOnlyTrackedDataList retrieved = storageManager.readTrackedDataList().get();
        assertEquals(original, new TrackedDataList(retrieved));
    }

    @Test
    public void trackedDataReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlTrackedDataStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlTrackedDataStorage} class.
         */
        TrackedData original = getTypicalTrackedData();
        storageManager.saveTrackedData(original);
        ReadOnlyTrackedData retrieved = storageManager.readTrackedData().get();
        assertEquals(original, new TrackedData(retrieved));
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void getWorkoutBookFilePath() {
        assertNotNull(storageManager.getWorkoutBookFilePath());
    }

    @Test
    public void getTrackedDataListFilePath() {
        assertNotNull(storageManager.getTrackedDataListFilePath());
    }

    @Test
    public void getTrackedDataFilePath() {
        assertNotNull(storageManager.getTrackedDataFilePath());
    }

    @Test
    public void handleWorkoutBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlWorkoutBookStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTrackedDataListStorage(Paths.get("dummy")),
                new XmlTrackedDataStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleWorkoutBookChangedEvent(new WorkoutBookChangedEvent(new WorkoutBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleTrackedDataListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlWorkoutBookStorage(Paths.get("dummy")),
                new XmlTrackedDataListStorageExceptionThrowingStub(Paths.get("dummy")),
                new XmlTrackedDataStorage(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleTrackedDataListChangedEvent(new TrackedDataListChangedEvent(new TrackedDataList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleTrackedDataChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlWorkoutBookStorage(Paths.get("dummy")),
                new XmlTrackedDataListStorage(Paths.get("dummy")),
                new XmlTrackedDataStorageExceptionThrowingStub(Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleTrackedDataChangedEvent(new TrackedDataChangedEvent(new TrackedData()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlWorkoutBookStorageExceptionThrowingStub extends XmlWorkoutBookStorage {

        public XmlWorkoutBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveWorkoutBook(ReadOnlyWorkoutBook workoutBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTrackedDataListStorageExceptionThrowingStub extends XmlTrackedDataListStorage {

        public XmlTrackedDataListStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTrackedDataList(ReadOnlyTrackedDataList trackedDataList, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlTrackedDataStorageExceptionThrowingStub extends XmlTrackedDataStorage {

        public XmlTrackedDataStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveTrackedData(ReadOnlyTrackedData trackedData, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

}
