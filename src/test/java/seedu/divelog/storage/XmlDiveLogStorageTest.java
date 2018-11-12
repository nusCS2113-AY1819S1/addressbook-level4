package seedu.divelog.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.divelog.testutil.TypicalDiveSessions.DIVE_AT_NIGHT;
import static seedu.divelog.testutil.TypicalDiveSessions.DIVE_AT_NOON;
import static seedu.divelog.testutil.TypicalDiveSessions.getTypicalDiveLog;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.divelog.commons.exceptions.DataConversionException;
import seedu.divelog.model.DiveLog;
import seedu.divelog.model.ReadOnlyDiveLog;

public class XmlDiveLogStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlDiveLogStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readDiveLog_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readDiveLog(null);
    }

    private java.util.Optional<ReadOnlyDiveLog> readDiveLog(String filePath) throws Exception {
        return new XmlDiveLogStorage(Paths.get(filePath)).readDiveLog(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDiveLog("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readDiveLog("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readDiveLog_invalidDiveLog_throwDataConversionException() throws Exception {
        //thrown.expect(DataConversionException.class);
        //readDiveLog("invalidDiveDiveLogBook.xml");
    }

    @Test
    public void readDiveLog_invalidAndValidDiveLog_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDiveLog("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAndSaveDiveLog_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempDiveLog.xml");
        DiveLog original = getTypicalDiveLog();
        XmlDiveLogStorage xmlDiveLogStorage = new XmlDiveLogStorage(filePath);

        //Save in new file and read back
        xmlDiveLogStorage.saveDiveLog(original, filePath);
        ReadOnlyDiveLog readBack = xmlDiveLogStorage.readDiveLog(filePath).get();
        assertEquals(original, new DiveLog(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addDive(DIVE_AT_NIGHT);
        original.recalculatePressureGroups();
        xmlDiveLogStorage.saveDiveLog(original, filePath);
        readBack = xmlDiveLogStorage.readDiveLog(filePath).get();
        assertEquals(original, new DiveLog(readBack));

        //Save and read without specifying file path
        original.addDive(DIVE_AT_NOON);
        original.recalculatePressureGroups();
        xmlDiveLogStorage.saveDiveLog(original); //file path not specified
        readBack = xmlDiveLogStorage.readDiveLog().get(); //file path not specified
        assertEquals(original, new DiveLog(readBack));

    }

    @Test
    public void saveDiveLog_nullDiveLog_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDiveLog(null, "SomeFile.xml");
    }

    /**
     * Saves {@code diveLog} at the specified {@code filePath}.
     */
    private void saveDiveLog(ReadOnlyDiveLog diveLog, String filePath) {
        try {
            new XmlDiveLogStorage(Paths.get(filePath))
                    .saveDiveLog(diveLog, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDiveLog_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDiveLog(new DiveLog(), null);
    }


}
