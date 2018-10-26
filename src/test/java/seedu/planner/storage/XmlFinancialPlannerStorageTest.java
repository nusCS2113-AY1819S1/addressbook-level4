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
import seedu.planner.model.record.DateBasedLimitList;
import seedu.planner.model.record.UniqueRecordList;
import seedu.planner.model.summary.SummaryMap;

public class XmlFinancialPlannerStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "XmlFinancialPlannerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    //TODO: Test(readRecordList, readLimitList, readSummaryMap throws null pointer exception)
    @Test
    public void readFinancialPlanner_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readFinancialPlanner(null);
    }

    @Test
    public void readRecordList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRecordList(null);
    }

    @Test
    public void readLimitList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readLimitList(null);
    }

    @Test
    public void readSummaryMap_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readSummaryMap(null);
    }

    //TODO: Test(read utility functions)
    private java.util.Optional<ReadOnlyFinancialPlanner> readFinancialPlanner(String filePath) throws Exception {
        return new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                .readFinancialPlanner(addToTestDataPathIfNotNull(filePath), addToTestDataPathIfNotNull(filePath));
    }

    private java.util.Optional<UniqueRecordList> readRecordList(String filePath) throws Exception {
        return new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                .readRecordList(addToTestDataPathIfNotNull(filePath));
    }

    private java.util.Optional<DateBasedLimitList> readLimitList(String filePath) throws Exception {
        return new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                .readLimitList(addToTestDataPathIfNotNull(filePath));
    }

    private java.util.Optional<SummaryMap> readSummaryMap(String filePath) throws Exception {
        return new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                .readSummaryMap(addToTestDataPathIfNotNull(filePath));
    }

    //TODO: Test(path generator)
    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    //TODO: Test(missing files)
    @Test
    public void read_missingFiles_emptyResult() throws Exception {
        assertFalse(readFinancialPlanner("NonExistentFile.xml").isPresent());
        assertFalse(readRecordList("NonExistentFile.xml").isPresent());
        assertFalse(readLimitList("NonExistentFile.xml").isPresent());
        assertFalse(readSummaryMap("NonExistentFile.xml").isPresent());
    }

    //TODO: Test(not XML)
    @Test
    public void read_recordListNotXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRecordList("NotXmlFormatRecordList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void read_summaryMapNotXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readSummaryMap("NotXmlFormatSummaryMap.xml");
    }


    @Test
    public void read_limitListNotXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readLimitList("NotXmlFormatLimitList.xml");
    }

    //TODO: Test(not correct format)
    @Test
    public void readRecordList_invalidRecordRecordList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRecordList("invalidRecord_RecordList.xml");
    }

    @Test
    public void readLimitList_invalidLimitLimitList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readLimitList("invalidLimit_LimitList.xml");
    }

    @Test
    public void readSummaryMap_invalidSummarySummaryMap_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readSummaryMap("invalidSummary_SummaryMap.xml");
    }

    //TODO: Test(incorrect then correct format)
    @Test
    public void readRecordList_invalidAndValidRecordRecordList_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readFinancialPlanner("invalidAndValidRecordRecordList.xml");
    }

    @Test
    public void readLimitList_invalidAndValidLimitLimitList_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readLimitList("invalidAndValidLimitLimitList.xml");
    }

    @Test
    public void readSummaryMap_invalidAndValidSummarySummaryMap_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readFinancialPlanner("invalidAndValidSummarySummaryMap.xml");
    }

    //TODO: Refactor the storage into 3 different storage
    @Test
    public void readAndSaveFinancialPlanner_allInOrder_success() throws Exception {
        Path recordListFilePath = testFolder.getRoot().toPath().resolve("TempFinancialPlanner.xml");
        Path summaryMapFilePath = testFolder.getRoot().toPath().resolve("TempSummaryMap.xml");
        Path limitListFilePath = testFolder.getRoot().toPath().resolve("TempLimitList.xml");

        FinancialPlanner original = getTypicalFinancialPlanner();
        XmlFinancialPlannerStorage xmlFinancialPlannerStorage = new XmlFinancialPlannerStorage(recordListFilePath,
                summaryMapFilePath, limitListFilePath);

        //Save in new file and read back, save each file separately
        xmlFinancialPlannerStorage.saveRecordList(original, recordListFilePath);
        xmlFinancialPlannerStorage.saveLimitList(original, limitListFilePath);
        xmlFinancialPlannerStorage.saveSummaryMap(original, summaryMapFilePath);

        //Read back the data into 3 components
        UniqueRecordList recordList = xmlFinancialPlannerStorage.readRecordList(recordListFilePath).get();
        DateBasedLimitList limitList = xmlFinancialPlannerStorage.readLimitList(limitListFilePath).get();
        SummaryMap summaryMap = xmlFinancialPlannerStorage.readSummaryMap(summaryMapFilePath).get();

        //Recreate the readback financial planner
        FinancialPlanner readBack = new FinancialPlanner();
        //TODO: @Oscar add your limit in resetdata
        readBack.resetData(recordList, summaryMap);
        assertEquals(original, new FinancialPlanner(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addRecord(BURSARY);
        original.addRecordToSummary(BURSARY);
        original.removeRecord(INDO);
        original.removeRecordFromSummary(INDO);
        xmlFinancialPlannerStorage.saveRecordList(original, recordListFilePath);
        xmlFinancialPlannerStorage.saveSummaryMap(original, summaryMapFilePath);
        xmlFinancialPlannerStorage.saveLimitList(original, limitListFilePath);
        recordList = xmlFinancialPlannerStorage.readRecordList(recordListFilePath).get();
        limitList = xmlFinancialPlannerStorage.readLimitList(limitListFilePath).get();
        summaryMap = xmlFinancialPlannerStorage.readSummaryMap(summaryMapFilePath).get();
        readBack = new FinancialPlanner();
        readBack.resetData(recordList, summaryMap);
        assertEquals(original, new FinancialPlanner(readBack));

        //Save and read without specifying file path
        original.addRecord(IDA);
        xmlFinancialPlannerStorage.saveRecordList(original); //file path not specified
        xmlFinancialPlannerStorage.saveLimitList(original);
        xmlFinancialPlannerStorage.saveSummaryMap(original);
        recordList = xmlFinancialPlannerStorage.readRecordList(recordListFilePath).get();
        limitList = xmlFinancialPlannerStorage.readLimitList(limitListFilePath).get();
        summaryMap = xmlFinancialPlannerStorage.readSummaryMap(summaryMapFilePath).get();
        readBack = new FinancialPlanner();
        readBack.resetData(recordList, summaryMap);
        assertEquals(original, new FinancialPlanner(readBack));
    }

    //TODO: test(savenullobject_throwsNull)
    @Test
    public void saveRecordList_nullRecordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRecordList(null, "SomeFile.xml");
    }

    @Test
    public void saveLimitList_nullLimitList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLimitList(null, "SomeFile.xml");
    }

    @Test
    public void saveSummaryMap_nullSummaryMap_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveSummaryMap(null, "SomeFile.xml");
    }

    //TODO:test(save everything) utility
    /**
     * Saves record list of {@code financialPlanner} at the specified {@code filePath}.
     */
    private void saveRecordList(ReadOnlyFinancialPlanner financialPlanner, String filePath) {
        try {
            new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                    .saveRecordList(financialPlanner, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Saves limit list of {@code financialPlanner} at the specified {@code filePath}.
     */
    private void saveLimitList(ReadOnlyFinancialPlanner financialPlanner, String filePath) {
        try {
            new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                    .saveLimitList(financialPlanner, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    /**
     * Saves summary map of {@code financialPlanner} at the specified {@code filePath}.
     */
    private void saveSummaryMap(ReadOnlyFinancialPlanner financialPlanner, String filePath) {
        try {
            new XmlFinancialPlannerStorage(Paths.get(filePath), Paths.get(filePath), Paths.get(filePath))
                    .saveSummaryMap(financialPlanner, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    //TODO: test(save without filepath)
    @Test
    public void saveRecordList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRecordList(new FinancialPlanner(), null);
    }

    @Test
    public void saveLimitList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLimitList(new FinancialPlanner(), null);
    }

    @Test
    public void saveSummaryMap_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveSummaryMap(new FinancialPlanner(), null);
    }
}
