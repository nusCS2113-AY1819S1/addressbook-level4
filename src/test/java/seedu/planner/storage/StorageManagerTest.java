package seedu.planner.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.events.model.DirectoryPathChangedEvent;
import seedu.planner.commons.events.model.FinancialPlannerChangedEvent;
import seedu.planner.commons.events.model.LimitListChangedEvent;
import seedu.planner.commons.events.model.SummaryMapChangedEvent;
import seedu.planner.commons.events.storage.DataSavingExceptionEvent;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.UserPrefs;
import seedu.planner.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;
    private Logger logger = LogsCenter.getLogger(StorageManagerTest.class);

    @Before
    public void setUp() {
        XmlFinancialPlannerStorage financialPlannerStorage = new XmlFinancialPlannerStorage(
                getTempFilePath("ab"), getTempFilePath("summaryMap"),
                getTempFilePath("limitList"), getTempFilePath("directoryPath"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(financialPlannerStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        logger.info("\n" + testFolder.getRoot().toPath().resolve(fileName).toString() + " Test folder getTempFilePath");
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
    public void financialPlannerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlFinancialPlannerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlFinancialPlannerStorageTest} class.
         */
        FinancialPlanner original = getTypicalFinancialPlanner();
        storageManager.saveFinancialPlanner(original);
        ReadOnlyFinancialPlanner retrieved = storageManager.readFinancialPlanner().get();
        assertEquals(original, new FinancialPlanner(retrieved));
    }

    @Test
    public void getRecordListFilePath() {
        assertNotNull(storageManager.getRecordListFilePath());
    }

    @Test
    public void getSummaryMapFilePath() {
        assertNotNull(storageManager.getSummaryMapFilePath());
    }

    @Test
    public void getLimitListFilePath() {
        assertNotNull(storageManager.getLimitListFilePath());
    }

    @Test
    public void getDirectoryFilePath() {
        assertNotNull(storageManager.getDirectoryPathFilePath());
    }

    @Test
    public void handleFinancialPlannerChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlFinancialPlannerStorageExceptionThrowingStub(
                Paths.get("dummy"),
                Paths.get("dummy"),
                Paths.get("dummy"),
                Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleFinancialPlannerChangedEvent(new FinancialPlannerChangedEvent(new FinancialPlanner()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleSummaryMapChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlFinancialPlannerStorageExceptionThrowingStub(
                Paths.get("dummy"),
                Paths.get("dummy"),
                Paths.get("dummy"),
                Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleSummaryMapChangedEvent(new SummaryMapChangedEvent(new FinancialPlanner()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleLimitListChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlFinancialPlannerStorageExceptionThrowingStub(
                Paths.get("dummy"),
                Paths.get("dummy"),
                Paths.get("dummy"),
                Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleLimitListChangedEvent(new LimitListChangedEvent(new FinancialPlanner()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleDirectoryPathChangedEvent_exceptionThrown_eventRaised() {
        Storage storage = new StorageManager(new XmlFinancialPlannerStorageExceptionThrowingStub(
                Paths.get("dummy"),
                Paths.get("dummy"),
                Paths.get("dummy"),
                Paths.get("dummy")),
                new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleDirectoryPathChangedEvent(new DirectoryPathChangedEvent(new FinancialPlanner()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlFinancialPlannerStorageExceptionThrowingStub extends XmlFinancialPlannerStorage {

        public XmlFinancialPlannerStorageExceptionThrowingStub(Path recordListFilePath, Path limitListFilePath,
                                                               Path summaryMapFilePath, Path diretoryPathFilePath) {
            super(recordListFilePath, summaryMapFilePath, limitListFilePath, diretoryPathFilePath);
        }

        @Override
        public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner) throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void saveRecordList(ReadOnlyFinancialPlanner financialPlanner, Path recordListFilePath)
                throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void saveLimitList(ReadOnlyFinancialPlanner financialPlanner, Path limitListFilePath)
                throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void saveSummaryMap(ReadOnlyFinancialPlanner financialPlanner, Path summaryMapFilePath)
                throws IOException {
            throw new IOException("dummy exception");
        }

        @Override
        public void saveDirectoryPath(ReadOnlyFinancialPlanner financialPlanner, Path directoryPathFilePath)
                throws IOException {
            throw new IOException("dummy exception");
        }
    }
}
