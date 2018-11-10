package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalEvents.EVENT_1;
import static seedu.address.testutil.TypicalEvents.EVENT_6;
import static seedu.address.testutil.TypicalEvents.getTypicalEventList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.EventList;
import seedu.address.model.ReadOnlyEventList;

//@@author: IcedCoffeeBoy

public class XmlEventListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlEventListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEventList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEventList(null);
    }

    private java.util.Optional<ReadOnlyEventList> readEventList(String filePath) throws Exception {
        return new XmlEventStorage(Paths.get(filePath)).readEventList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEventList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEventList("NotXmlFormatEventList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readEventList_invalidEventEventList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEventList("invalidEventEventList.xml");
    }

    @Test
    public void readEventList_invalidAndValidEventEventList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEventList("invalidAndValidEventEventList.xml");
    }

    @Test
    public void readAndSaveEventList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempEventList.xml");
        EventList original = getTypicalEventList();
        XmlEventStorage xmlEventStorage = new XmlEventStorage(filePath);

        //Save in new file and read back
        xmlEventStorage.saveEventList(original, filePath);
        ReadOnlyEventList readBack = xmlEventStorage.readEventList(filePath).get();
        assertEquals(original, new EventList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addEvent(EVENT_6);
        original.removeEvent(EVENT_1);
        xmlEventStorage.saveEventList(original, filePath);
        readBack = xmlEventStorage.readEventList(filePath).get();
        assertEquals(original, new EventList(readBack));

        //Save and read without specifying file path
        original.addEvent(EVENT_1);
        xmlEventStorage.saveEventList(original); //file path not specified
        readBack = xmlEventStorage.readEventList().get(); //file path not specified
        assertEquals(original, new EventList(readBack));

    }

    @Test
    public void saveEventList_nullEventList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveEventList(ReadOnlyEventList eventList, String filePath) {
        try {
            new XmlEventStorage(Paths.get(filePath))
                    .saveEventList(eventList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEventList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEventList(new EventList(), null);
    }


}
