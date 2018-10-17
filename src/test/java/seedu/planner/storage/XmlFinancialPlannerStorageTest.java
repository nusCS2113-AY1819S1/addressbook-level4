package seedu.planner.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.planner.testutil.TypicalRecords.BURSARY;
import static seedu.planner.testutil.TypicalRecords.IDA;
import static seedu.planner.testutil.TypicalRecords.INDO;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.ReadOnlyFinancialPlanner;

public class XmlFinancialPlannerStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "XmlFinancialPlannerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readFinancialPlanner_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readFinancialPlanner(null);
    }

    private java.util.Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(String filePath) throws Exception {
        return new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                .readFinancialPlanner(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFinancialPlanner("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readFinancialPlanner("NotXmlFormatFinancialPlanner.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readFinancialPlanner_invalidRecordFinancialPlanner_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readFinancialPlanner("invalidRecordFinancialPlanner.xml");
    }

    @Test
    public void readFinancialPlanner_invalidAndValidRecordFinancialPlanner_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readFinancialPlanner("invalidAndValidRecordFinancialPlanner.xml");
    }

    @Test
    public void readAndSaveFinancialPlanner_allInOrder_success() throws Exception {
        Path filePathRecordList = testFolder.getRoot().toPath().resolve("TempFinancialPlanner.xml");
        Path filePathSummaryMap = testFolder.getRoot().toPath().resolve("TempSummaryMap.xml");
        Path filePathLimitList = testFolder.getRoot().toPath().resolve("TempLimitList.xml");
        FinancialPlanner original = getTypicalFinancialPlanner();
        XmlFinancialPlannerStorage xmlFinancialPlannerStorage = new XmlFinancialPlannerStorage(filePathRecordList, filePathSummaryMap, filePathLimitList);

        //Save in new file and read back
        xmlFinancialPlannerStorage.saveFinancialPlanner(original, filePathRecordList);
        ReadOnlyFinancialPlanner readBack = xmlFinancialPlannerStorage.readFinancialPlanner(filePathRecordList).get();
        assertEquals(original, new FinancialPlanner(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addRecord(BURSARY);
        original.removeRecord(INDO);
        xmlFinancialPlannerStorage.saveFinancialPlanner(original, filePathRecordList);
        readBack = xmlFinancialPlannerStorage.readFinancialPlanner(filePathRecordList).get();
        assertEquals(original, new FinancialPlanner(readBack));

        //Save and read without specifying file path
        original.addRecord(IDA);
        xmlFinancialPlannerStorage.saveRecordList(original); //file path not specified
        readBack = xmlFinancialPlannerStorage.readFinancialPlanner().get(); //file path not specified
        assertEquals(original, new FinancialPlanner(readBack));

    }

    @Test
    public void saveFinancialPlanner_nullFinancialPlanner_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveFinancialPlanner(null, "SomeFile.xml");
    }

    /**
     * Saves {@code financialPlanner} at the specified {@code filePath}.
     */
    private void saveFinancialPlanner(ReadOnlyFinancialPlanner financialPlanner, String filePath) {
        try {
            new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                    .saveFinancialPlanner(financialPlanner, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFinancialPlanner_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveFinancialPlanner(new FinancialPlanner(), null);
    }


}
