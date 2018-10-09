package seedu.recruit.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.recruit.commons.events.model.CandidateBookChangedEvent;
import seedu.recruit.commons.events.storage.DataSavingExceptionEvent;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.ReadOnlyCandidateBook;
import seedu.recruit.model.UserPrefs;
import seedu.recruit.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlCandidateBookStorage candidateBookStorage = new XmlCandidateBookStorage(getTempFilePath("ab"));
        XmlCompanyBookStorage companyBookStorage = new XmlCompanyBookStorage(getTempFilePath("cd"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(candidateBookStorage, companyBookStorage, userPrefsStorage);
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
         * {@link XmlCandidateBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlCandidateBookStorageTest} class.
         */
        CandidateBook original = getTypicalAddressBook();
        storageManager.saveCandidateBook(original);
        ReadOnlyCandidateBook retrieved = storageManager.readCandidateBook().get();
        assertEquals(original, new CandidateBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getCandidateBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlCandidateBookStorageExceptionThrowingStub(Paths.get("dummy")),
                                            new XmlCompanyBookStorage(getTempFilePath("cd")),
                                            new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleCandidateBookChangedEvent(new CandidateBookChangedEvent(new CandidateBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlCandidateBookStorageExceptionThrowingStub extends XmlCandidateBookStorage {

        public XmlCandidateBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCandidateBook(ReadOnlyCandidateBook addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
