package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalWorkouts.HOON_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.IDA_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.getTypicalTrackedData;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackedData;
import seedu.address.model.TrackedData;

public class XmlTrackedDataStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlTrackedDataStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTrackedData_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTrackedData(null);
    }

    private java.util.Optional<ReadOnlyTrackedData> readTrackedData(String filePath) throws Exception {
        return new XmlTrackedDataStorage(Paths.get(filePath)).readTrackedData(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTrackedData("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTrackedData("NotXmlFormatTrackedData.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readTrackedData_invalidWorkoutTrackedData_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTrackedData("invalidWorkoutTrackedData.xml");
    }

    @Test
    public void readTrackedData_invalidAndValidWorkoutTrackedData_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTrackedData("invalidAndValidWorkoutTrackedData.xml");
    }

    @Test
    public void readAndSaveTrackedData_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTrackedData.xml");
        TrackedData original = getTypicalTrackedData();
        XmlTrackedDataStorage xmlTrackedDataStorage = new XmlTrackedDataStorage(filePath);

        //Save in new file and read back
        xmlTrackedDataStorage.saveTrackedData(original, filePath);
        ReadOnlyTrackedData readBack = xmlTrackedDataStorage.readTrackedData(filePath).get();
        assertEquals(original, new TrackedData(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addWorkout(HOON_WORKOUT);
        xmlTrackedDataStorage.saveTrackedData(original, filePath);
        readBack = xmlTrackedDataStorage.readTrackedData(filePath).get();
        assertEquals(original, new TrackedData(readBack));

        //Save and read without specifying file path
        original.addWorkout(IDA_WORKOUT);
        xmlTrackedDataStorage.saveTrackedData(original); //file path not specified
        readBack = xmlTrackedDataStorage.readTrackedData().get(); //file path not specified
        assertEquals(original, new TrackedData(readBack));

    }

    @Test
    public void saveTrackedData_nullTrackedData_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTrackedData(null, "SomeFile.xml");
    }

    /**
     * Saves {@code trackedData} at the specified {@code filePath}.
     */
    private void saveTrackedData(ReadOnlyTrackedData trackedData, String filePath) {
        try {
            new XmlTrackedDataStorage(Paths.get(filePath))
                    .saveTrackedData(trackedData, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTrackedData_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTrackedData(new TrackedData(), null);
    }


}
