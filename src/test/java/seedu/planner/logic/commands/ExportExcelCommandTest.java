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

//@author nguyenngoclinhchi
public class ExportExcelCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Date startDate1 = TypicalRecords.TYPICAL_START_DATE1;
        Date startDate2 = TypicalRecords.TYPICAL_START_DATE1;
        Date endDate1 = TypicalRecords.TYPICAL_END_DATE;
        Date endDate2 = TypicalRecords.TYPICAL_END_DATE1;
        String directoryPath = HOME_DIRECTORY_STRING;
        String directoryPath1 = DirectoryPath.WORKING_DIRECTORY_STRING;

        ExportExcelCommand exportExcelCommand11 = new ExportExcelCommand();
        ExportExcelCommand exportExcelCommand21 = new ExportExcelCommand(directoryPath);
        ExportExcelCommand exportExcelCommand31 = new ExportExcelCommand(startDate1, endDate1);
        ExportExcelCommand exportExcelCommand41 = new ExportExcelCommand(startDate1, endDate1, directoryPath);

        ExportExcelCommand exportExcelCommand22 = new ExportExcelCommand(directoryPath1);
        ExportExcelCommand exportExcelCommand32 = new ExportExcelCommand(startDate2, endDate2);
        ExportExcelCommand exportExcelCommand42 = new ExportExcelCommand(startDate2, endDate2, directoryPath1);

        // same object -> returns true
        Assert.assertTrue(exportExcelCommand11.equals(exportExcelCommand11));
        Assert.assertTrue(exportExcelCommand21.equals(exportExcelCommand21));
        Assert.assertTrue(exportExcelCommand31.equals(exportExcelCommand31));
        Assert.assertTrue(exportExcelCommand41.equals(exportExcelCommand41));

        // same value -> returns true
        ExportExcelCommand exportExcelCommand11Copy = new ExportExcelCommand();
        ExportExcelCommand exportExcelCommand21Copy = new ExportExcelCommand(directoryPath);
        ExportExcelCommand exportExcelCommand31Copy = new ExportExcelCommand(startDate1, endDate1);
        ExportExcelCommand exportExcelCommand41Copy = new ExportExcelCommand(startDate1, endDate1, directoryPath);

        Assert.assertTrue(exportExcelCommand11.equals(exportExcelCommand11Copy));
        Assert.assertTrue(exportExcelCommand21.equals(exportExcelCommand21Copy));
        Assert.assertTrue(exportExcelCommand31.equals(exportExcelCommand31Copy));
        Assert.assertTrue(exportExcelCommand41.equals(exportExcelCommand41Copy));

        //different value -> returns false
        Assert.assertFalse(exportExcelCommand31.equals(exportExcelCommand32));
        Assert.assertFalse(exportExcelCommand41.equals(exportExcelCommand42));

        // different types -> returns false
        Assert.assertFalse(exportExcelCommand41.equals(Arrays.asList(startDate1, endDate1, directoryPath)));

        // null -> returns false
        Assert.assertFalse(exportExcelCommand11.equals(null));
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
