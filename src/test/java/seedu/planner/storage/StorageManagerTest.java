package seedu.planner.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.planner.commons.events.model.FinancialPlannerChangedEvent;
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

    @Before
    public void setUp() {
        XmlFinancialPlannerStorage financialPlannerStorage = new XmlFinancialPlannerStorage(getTempFilePath("ab"), getTempFilePath("summaryMap")
        , getTempFilePath("limitList"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(financialPlannerStorage, userPrefsStorage);
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
    public void financialPlannerReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlFinancialPlannerStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlFinancialPlannerStorageTest} class.
         */
        FinancialPlanner original = getTypicalFinancialPlanner();
        storageManager.saveRecordList(original);
        ReadOnlyFinancialPlanner retrieved = storageManager.readFinancialPlanner().get();
        assertEquals(original, new FinancialPlanner(retrieved));
    }

    @Test
    public void getFinancialPlannerFilePath() {
        assertNotNull(storageManager.getFinancialPlannerFilePath());
    }

    @Test
    public void handleFinancialPlannerChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlFinancialPlannerStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleFinancialPlannerChangedEvent(new FinancialPlannerChangedEvent(new FinancialPlanner()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlFinancialPlannerStorageExceptionThrowingStub extends XmlFinancialPlannerStorage {

        public XmlFinancialPlannerStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
