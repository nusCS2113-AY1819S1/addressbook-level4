package com.t13g2.forum.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.t13g2.forum.commons.events.model.AddressBookChangedEvent;
import com.t13g2.forum.commons.events.storage.DataSavingExceptionEvent;
import com.t13g2.forum.model.ForumBook;
import com.t13g2.forum.model.ReadOnlyForumBook;
import com.t13g2.forum.model.UserPrefs;
import com.t13g2.forum.testutil.TypicalPersons;
import com.t13g2.forum.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlForumBookStorage addressBookStorage = new XmlForumBookStorage(getTempFilePath("ab"));
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
         * {@link XmlForumBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlForumBookStorageTest} class.
         */
        ForumBook original = TypicalPersons.getTypicalAddressBook();
        storageManager.saveForumBook(original);
        ReadOnlyForumBook retrieved = storageManager.readForumBook().get();
        assertEquals(original, new ForumBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getForumBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlForumBookStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new ForumBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlForumBookStorageExceptionThrowingStub extends XmlForumBookStorage {

        public XmlForumBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveForumBook(ReadOnlyForumBook addressBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
