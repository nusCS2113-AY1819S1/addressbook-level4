package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.planner.commons.events.ui.ExitAppRequestEvent;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.commons.util.XmlUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.FinancialPlanner;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.record.UniqueRecordList;
import seedu.planner.model.summary.SummaryMap;
import seedu.planner.storage.xmljaxb.XmlAdaptedRecord;
import seedu.planner.storage.xmljaxb.XmlAdaptedTag;
import seedu.planner.storage.xmljaxb.XmlSerializableFinancialPlanner;
import seedu.planner.testutil.FinancialPlannerBuilder;
import seedu.planner.testutil.RecordBuilder;
import seedu.planner.testutil.TestUtil;
import seedu.planner.ui.testutil.EventsCollectorRule;

public class ExitCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult result = new ExitCommand().execute(model, commandHistory);
        assertEquals(MESSAGE_EXIT_ACKNOWLEDGEMENT, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExitAppRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    public static class XmlUtilTest {

        private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
        private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
        private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
        private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validFinancialPlanner.xml");
        private static final Path MISSING_RECORD_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingRecordField.xml");
        private static final Path INVALID_RECORD_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidRecordField.xml");
        private static final Path VALID_RECORD_FILE = TEST_DATA_FOLDER.resolve("validRecord.xml");
        private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempFinancialPlanner.xml");

        private static final String INVALID_DATE = "9482asf424";

        private static final String VALID_NAME = "Payment for HM";
        private static final String VALID_DATE = "23-10-2010";
        private static final String VALID_MONEYFLOW = "-11.99";
        private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

        @Rule
        public ExpectedException thrown = ExpectedException.none();

        @Test
        public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
            thrown.expect(NullPointerException.class);
            XmlUtil.getDataFromFile(null, FinancialPlanner.class);
        }

        @Test
        public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
            thrown.expect(NullPointerException.class);
            XmlUtil.getDataFromFile(VALID_FILE, null);
        }

        @Test
        public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
            thrown.expect(FileNotFoundException.class);
            XmlUtil.getDataFromFile(MISSING_FILE, FinancialPlanner.class);
        }

        @Test
        public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
            thrown.expect(JAXBException.class);
            XmlUtil.getDataFromFile(EMPTY_FILE, FinancialPlanner.class);
        }

        //TODO: @test change this to non hardcoded
        @Test
        public void getDataFromFile_validFile_validResult() throws Exception {
            UniqueRecordList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableFinancialPlanner.class)
                    .toModelType();
            FinancialPlanner data = new FinancialPlanner();
            data.resetData(dataFromFile, new SummaryMap());
            assertEquals(9, data.getRecordList().size());
        }

        @Test
        public void xmlAdaptedRecordFromFile_fileWithMissingRecordField_validResult() throws Exception {
            XmlAdaptedRecord actualRecord = XmlUtil.getDataFromFile(
                    MISSING_RECORD_FIELD_FILE, XmlAdaptedRecordWithRootElement.class);
            XmlAdaptedRecord expectedRecord = new XmlAdaptedRecord(
                    null, VALID_DATE, VALID_MONEYFLOW, VALID_TAGS);
            assertEquals(expectedRecord, actualRecord);
        }

        @Test
        public void xmlAdaptedRecordFromFile_fileWithInvalidRecordField_validResult() throws Exception {
            XmlAdaptedRecord actualRecord = XmlUtil.getDataFromFile(
                    INVALID_RECORD_FIELD_FILE, XmlAdaptedRecordWithRootElement.class);
            XmlAdaptedRecord expectedRecord = new XmlAdaptedRecord(
                    VALID_NAME, INVALID_DATE, VALID_MONEYFLOW, VALID_TAGS);
            assertEquals(expectedRecord, actualRecord);
        }

        @Test
        public void xmlAdaptedRecordFromFile_fileWithValidRecord_validResult() throws Exception {
            XmlAdaptedRecord actualRecord = XmlUtil.getDataFromFile(
                    VALID_RECORD_FILE, XmlAdaptedRecordWithRootElement.class);
            XmlAdaptedRecord expectedRecord = new XmlAdaptedRecord(
                    VALID_NAME, VALID_DATE, VALID_MONEYFLOW, VALID_TAGS);
            assertEquals(expectedRecord, actualRecord);
        }

        @Test
        public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
            thrown.expect(NullPointerException.class);
            XmlUtil.saveDataToFile(null, new FinancialPlanner());
        }

        @Test
        public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
            thrown.expect(NullPointerException.class);
            XmlUtil.saveDataToFile(VALID_FILE, null);
        }

        @Test
        public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
            thrown.expect(FileNotFoundException.class);
            XmlUtil.saveDataToFile(MISSING_FILE, new FinancialPlanner());
        }

        @Test
        public void saveDataToFile_validFile_dataSaved() throws Exception {
            FileUtil.createFile(TEMP_FILE);
            XmlSerializableFinancialPlanner dataToWrite = new XmlSerializableFinancialPlanner(new FinancialPlanner());
            XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
            XmlSerializableFinancialPlanner dataFromFile = XmlUtil.getDataFromFile
                    (TEMP_FILE, XmlSerializableFinancialPlanner.class);
            assertEquals(dataToWrite, dataFromFile);

            FinancialPlannerBuilder builder = new FinancialPlannerBuilder(new FinancialPlanner());
            dataToWrite = new XmlSerializableFinancialPlanner(
                    builder.withRecord(new RecordBuilder().build()).build());

            XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
            dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableFinancialPlanner.class);
            assertEquals(dataToWrite, dataFromFile);
        }

        /**
         * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to
         * {@code XmlAdaptedRecord}
         * objects.
         */
        @XmlRootElement(name = "record")
        private static class XmlAdaptedRecordWithRootElement extends XmlAdaptedRecord {}
    }
}
