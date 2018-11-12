package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.schedule.TypicalSchedules.ALICE_WORK;
import static seedu.address.testutil.schedule.TypicalSchedules.AMY;
import static seedu.address.testutil.schedule.TypicalSchedules.BOB;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.schedule.ReadOnlyScheduleList;
import seedu.address.model.schedule.ScheduleList;
import seedu.address.storage.schedule.XmlScheduleListStorage;

public class XmlScheduleListStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlScheduleListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readScheduleList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readScheduleList(null);
    }

    private java.util.Optional<ReadOnlyScheduleList> readScheduleList(String filePath) throws Exception {
        return new XmlScheduleListStorage(Paths.get(filePath)).readScheduleList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readScheduleList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readScheduleList("NotXmlFormatScheduleList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readScheduleList_invalidScheduleScheduleList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readScheduleList("invalidScheduleScheduleList.xml");
    }

    @Test
    public void readScheduleList_invalidAndValidScheduleScheduleList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readScheduleList("invalidAndValidScheduleScheduleList.xml");
    }

    @Test
    public void readAndSaveScheduleList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        ScheduleList original = getTypicalScheduleList();
        XmlScheduleListStorage xmlScheduleListStorage = new XmlScheduleListStorage(filePath);

        //Save in new file and read back
        xmlScheduleListStorage.saveScheduleList(original, filePath);
        ReadOnlyScheduleList readBack = xmlScheduleListStorage.readScheduleList(filePath).get();
        assertEquals(original, new ScheduleList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addSchedule(AMY);
        original.removeSchedule(ALICE_WORK);
        xmlScheduleListStorage.saveScheduleList(original, filePath);
        readBack = xmlScheduleListStorage.readScheduleList(filePath).get();
        assertEquals(original, new ScheduleList(readBack));

        //Save and read without specifying file path
        original.addSchedule(BOB);
        xmlScheduleListStorage.saveScheduleList(original); //file path not specified
        readBack = xmlScheduleListStorage.readScheduleList().get(); //file path not specified
        assertEquals(original, new ScheduleList(readBack));
    }

    @Test
    public void saveScheduleList_nullScheduleList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveScheduleList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code scheduleList} at the specified {@code filePath}.
     */
    private void saveScheduleList(ReadOnlyScheduleList scheduleList, String filePath) {
        try {
            new XmlScheduleListStorage(Paths.get(filePath))
                    .saveScheduleList(scheduleList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveScheduleList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveScheduleList(new ScheduleList(), null);
    }

}
