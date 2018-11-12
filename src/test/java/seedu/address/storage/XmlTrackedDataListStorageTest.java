package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER_MANUAL;
import static seedu.address.testutil.TypicalParameters.TYPE_PARAMETER_MANUAL;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackedDataList;
import seedu.address.model.TrackedDataList;

public class XmlTrackedDataListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlTrackedDataListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTrackedDataList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTrackedDataList(null);
    }

    private java.util.Optional<ReadOnlyTrackedDataList> readTrackedDataList(String filePath) throws Exception {
        return new XmlTrackedDataListStorage(Paths.get(filePath))
                .readTrackedDataList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTrackedDataList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTrackedDataList("NotXmlFormatTrackedDataList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readTrackedDataList_invalidParameterTrackedDataList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTrackedDataList("invalidParameterTrackedDataList.xml");
    }

    @Test
    public void readTrackedDataList_invalidAndValidParameterTrackedDataList_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readTrackedDataList("invalidAndValidParameterTrackedDataList.xml");
    }

    @Test
    public void readAndSaveTrackedDataList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTrackedDataList.xml");
        TrackedDataList original = getTypicalTrackedDataList();
        XmlTrackedDataListStorage xmlTrackedDataListStorage = new XmlTrackedDataListStorage(filePath);

        //Save in new file and read back
        xmlTrackedDataListStorage.saveTrackedDataList(original, filePath);
        ReadOnlyTrackedDataList readBack = xmlTrackedDataListStorage.readTrackedDataList(filePath).get();
        assertEquals(original, new TrackedDataList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addParameter(NAME_PARAMETER_MANUAL);
        original.removeParameter(NAME_PARAMETER);
        xmlTrackedDataListStorage.saveTrackedDataList(original, filePath);
        readBack = xmlTrackedDataListStorage.readTrackedDataList(filePath).get();
        assertEquals(original, new TrackedDataList(readBack));

        //Save and read without specifying file path
        original.addParameter(TYPE_PARAMETER_MANUAL);
        xmlTrackedDataListStorage.saveTrackedDataList(original); //file path not specified
        readBack = xmlTrackedDataListStorage.readTrackedDataList().get(); //file path not specified
        assertEquals(original, new TrackedDataList(readBack));

    }

    @Test
    public void saveTrackedDataList_nullTrackedDataList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTrackedDataList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code trackedDataList} at the specified {@code filePath}.
     */
    private void saveTrackedDataList(ReadOnlyTrackedDataList trackedDataList, String filePath) {
        try {
            new XmlTrackedDataListStorage(Paths.get(filePath))
                    .saveTrackedDataList(trackedDataList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTrackedDataList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTrackedDataList(new TrackedDataList(), null);
    }


}
