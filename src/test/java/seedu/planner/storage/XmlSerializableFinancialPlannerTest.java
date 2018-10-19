package seedu.planner.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.commons.util.XmlUtil;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.summary.SummaryMap;
import seedu.planner.storage.xmljaxb.XmlSerializableFinancialPlanner;
import seedu.planner.testutil.TypicalRecords;

public class XmlSerializableFinancialPlannerTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "XmlSerializableFinancialPlannerTest");
    private static final Path TYPICAL_RECORDS_FILE = TEST_DATA_FOLDER.resolve("typicalRecordsFinancialPlanner.xml");
    private static final Path INVALID_RECORD_FILE = TEST_DATA_FOLDER.resolve("invalidSummary_SummaryMap.xml");
    private static final Path DUPLICATE_RECORD_FILE = TEST_DATA_FOLDER.resolve("duplicateRecordFinancialPlanner.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //TODO: @test Rewrite this test to not be hardcoded
    @Test
    public void toModelType_typicalRecordsFile_success() throws Exception {
        XmlSerializableFinancialPlanner dataFromFile = XmlUtil.getDataFromFile(
                TYPICAL_RECORDS_FILE, XmlSerializableFinancialPlanner.class);
        FinancialPlanner financialPlannerFromFile = new FinancialPlanner();
        financialPlannerFromFile.resetData(dataFromFile.toModelType(), new SummaryMap());
        FinancialPlanner typicalRecordsFinancialPlanner = TypicalRecords.getTypicalFinancialPlanner();
        assertEquals(financialPlannerFromFile, typicalRecordsFinancialPlanner);
    }

    @Test
    public void toModelType_invalidRecordFile_throwsIllegalValueException() throws Exception {
        XmlSerializableFinancialPlanner dataFromFile = XmlUtil.getDataFromFile(INVALID_RECORD_FILE,
                XmlSerializableFinancialPlanner.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateRecords_throwsIllegalValueException() throws Exception {
        XmlSerializableFinancialPlanner dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_RECORD_FILE,
                XmlSerializableFinancialPlanner.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableFinancialPlanner.MESSAGE_DUPLICATE_RECORD);
        dataFromFile.toModelType();
    }

}
