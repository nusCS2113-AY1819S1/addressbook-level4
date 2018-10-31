package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.model.DirectoryPath.HOME_DIRECTORY_STRING;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.util.ExcelUtil;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.DirectoryPath;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.TypicalRecords;

public class ExportExcelCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Date startDate_1 = TypicalRecords.TYPICAL_START_DATE1;
        Date startDate_2 = TypicalRecords.TYPICAL_START_DATE1;
        Date endDate_1 = TypicalRecords.TYPICAL_END_DATE;
        Date endDate_2 = TypicalRecords.TYPICAL_END_DATE1;
        String directoryPath = HOME_DIRECTORY_STRING;
        String directoryPath1 = DirectoryPath.WORKING_DIRECTORY_STRING;

        ExportExcelCommand exportExcelCommand_1_1 = new ExportExcelCommand();
        ExportExcelCommand exportExcelCommand_2_1 = new ExportExcelCommand(directoryPath);
        ExportExcelCommand exportExcelCommand_3_1 = new ExportExcelCommand(startDate_1, endDate_1);
        ExportExcelCommand exportExcelCommand_4_1 = new ExportExcelCommand(startDate_1, endDate_1, directoryPath);

        ExportExcelCommand exportExcelCommand_2_2 = new ExportExcelCommand(directoryPath1);
        ExportExcelCommand exportExcelCommand_3_2 = new ExportExcelCommand(startDate_2, endDate_2);
        ExportExcelCommand exportExcelCommand_4_2 = new ExportExcelCommand(startDate_2, endDate_2, directoryPath1);

        // same object -> returns true
        Assert.assertTrue(exportExcelCommand_1_1.equals(exportExcelCommand_1_1));
        Assert.assertTrue(exportExcelCommand_2_1.equals(exportExcelCommand_2_1));
        Assert.assertTrue(exportExcelCommand_3_1.equals(exportExcelCommand_3_1));
        Assert.assertTrue(exportExcelCommand_4_1.equals(exportExcelCommand_4_1));

        // same value -> returns true
        ExportExcelCommand exportExcelCommand_1_1_Copy = new ExportExcelCommand();
        ExportExcelCommand exportExcelCommand_2_1_Copy = new ExportExcelCommand(directoryPath);
        ExportExcelCommand exportExcelCommand_3_1_Copy = new ExportExcelCommand(startDate_1, endDate_1);
        ExportExcelCommand exportExcelCommand_4_1_Copy = new ExportExcelCommand(startDate_1, endDate_1, directoryPath);

        Assert.assertTrue(exportExcelCommand_1_1.equals(exportExcelCommand_1_1_Copy));
        Assert.assertTrue(exportExcelCommand_2_1.equals(exportExcelCommand_2_1_Copy));
        Assert.assertTrue(exportExcelCommand_3_1.equals(exportExcelCommand_3_1_Copy));
        Assert.assertTrue(exportExcelCommand_4_1.equals(exportExcelCommand_4_1_Copy));

        //different value -> returns false
        Assert.assertFalse(exportExcelCommand_3_1.equals(exportExcelCommand_3_2));
        Assert.assertFalse(exportExcelCommand_4_1.equals(exportExcelCommand_4_2));

        // different types -> returns false
        Assert.assertFalse(exportExcelCommand_4_1.equals(Arrays.asList(startDate_1, endDate_1, directoryPath)));

        // null -> returns false
        Assert.assertFalse(exportExcelCommand_1_1.equals(null));
    }

    @Test
    public void execute_zeroRecordFound_noRecordFound() {
        String nameFile = ExcelUtil.setNameExcelFile(
                TypicalRecords.TYPICAL_START_DATE1, TypicalRecords.TYPICAL_END_DATE1);
        ExportExcelCommand command = new ExportExcelCommand(
                TypicalRecords.TYPICAL_START_DATE1, TypicalRecords.TYPICAL_END_DATE1, HOME_DIRECTORY_STRING);
        String expectedMessage;
        expectedModel.updateFilteredRecordList(new DateIsWithinIntervalPredicate(
                TypicalRecords.TYPICAL_START_DATE1, TypicalRecords.TYPICAL_END_DATE1));
        List<Record> records = expectedModel.getFilteredRecordList();
        if (records.size() > 0) {
            expectedMessage = String.format(
                    Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY,
                    nameFile, DirectoryPath.HOME_DIRECTORY.getDirectoryPathValue());
        } else {
            expectedMessage = Messages.MESSAGE_EXPORT_COMMAND_ERRORS;
        }
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleRecordsFound() {
        String nameFile = ExcelUtil.setNameExcelFile(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE);
        String expectedMessage = String.format(
                Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, nameFile, HOME_DIRECTORY_STRING);
        ExportExcelCommand command = new ExportExcelCommand(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE, HOME_DIRECTORY_STRING);
        expectedModel.updateFilteredRecordList(new DateIsWithinIntervalPredicate(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
