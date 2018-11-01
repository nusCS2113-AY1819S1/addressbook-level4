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

public class ArchiveCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Date startDate1 = TypicalRecords.TYPICAL_START_DATE1;
        Date startDate2 = TypicalRecords.TYPICAL_START_DATE1;
        Date endDate1 = TypicalRecords.TYPICAL_END_DATE;
        Date endDate2 = TypicalRecords.TYPICAL_END_DATE1;
        String directoryPath = HOME_DIRECTORY_STRING;
        String directoryPath1 = DirectoryPath.WORKING_DIRECTORY_STRING;

        ArchiveCommand achieveCommand11 = new ArchiveCommand();
        ArchiveCommand achieveCommand21 = new ArchiveCommand(directoryPath);
        ArchiveCommand achieveCommand31 = new ArchiveCommand(startDate1, endDate1);
        ArchiveCommand achieveCommand41 = new ArchiveCommand(startDate1, endDate1, directoryPath);

        ArchiveCommand achieveCommand22 = new ArchiveCommand(directoryPath1);
        ArchiveCommand achieveCommand32 = new ArchiveCommand(startDate2, endDate2);
        ArchiveCommand achieveCommand42 = new ArchiveCommand(startDate2, endDate2, directoryPath1);

        // same object -> returns true
        Assert.assertTrue(achieveCommand11.equals(achieveCommand11));
        Assert.assertTrue(achieveCommand21.equals(achieveCommand21));
        Assert.assertTrue(achieveCommand31.equals(achieveCommand31));
        Assert.assertTrue(achieveCommand41.equals(achieveCommand41));

        // same value -> returns true
        ArchiveCommand achieveCommand11Copy = new ArchiveCommand();
        ArchiveCommand achieveExcelCommand21Copy = new ArchiveCommand(directoryPath);
        ArchiveCommand achieveExcelCommand31Copy = new ArchiveCommand(startDate1, endDate1);
        ArchiveCommand achieveExcelCommand41Copy = new ArchiveCommand(startDate1, endDate1, directoryPath);

        Assert.assertTrue(achieveCommand11.equals(achieveCommand11Copy));
        Assert.assertTrue(achieveCommand21.equals(achieveExcelCommand21Copy));
        Assert.assertTrue(achieveCommand31.equals(achieveExcelCommand31Copy));
        Assert.assertTrue(achieveCommand41.equals(achieveExcelCommand41Copy));

        //different value -> returns false
        Assert.assertFalse(achieveCommand31.equals(achieveCommand32));
        Assert.assertFalse(achieveCommand41.equals(achieveCommand42));

        // different types -> returns false
        Assert.assertFalse(achieveCommand41.equals(Arrays.asList(startDate1, endDate1, directoryPath)));

        // null -> returns false
        Assert.assertFalse(achieveCommand11.equals(null));
    }

    @Test
    public void execute_zeroRecordFound_noRecordFound() {
        Model expectedModel = model;
        String nameFile = ExcelUtil.setNameExcelFile(
                TypicalRecords.TYPICAL_START_DATE1, TypicalRecords.TYPICAL_END_DATE1);
        ArchiveCommand command = new ArchiveCommand(
                TypicalRecords.TYPICAL_START_DATE1, TypicalRecords.TYPICAL_END_DATE1);
        String expectedMessage;
        expectedModel.updateFilteredRecordList(new DateIsWithinIntervalPredicate(
                TypicalRecords.TYPICAL_START_DATE1, TypicalRecords.TYPICAL_END_DATE1));
        List<Record> records = expectedModel.getFilteredRecordList();
        if (records.size() > 0) {
            expectedMessage = String.format(
                    Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY,
                    nameFile, DirectoryPath.HOME_DIRECTORY_STRING);
            expectedModel.deleteListRecord(records);
            expectedModel.commitFinancialPlanner();
        } else {
            expectedMessage = Messages.MESSAGE_ACHIEVE_COMMAND_ERRORS;
        }
        expectedModel.getFinancialPlanner();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleRecordsFound() {
        Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        String nameFile = ExcelUtil.setNameExcelFile(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE);
        String expectedMessage = String.format(
                Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY + Messages.MESSAGE_ACHIEVE_SUCCESSFULLY,
                nameFile, HOME_DIRECTORY_STRING);
        ArchiveCommand command = new ArchiveCommand(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE, HOME_DIRECTORY_STRING);
        expectedModel.updateFilteredRecordList(new DateIsWithinIntervalPredicate(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE));
        List<Record> records = expectedModel.getFilteredRecordList();
        expectedModel.deleteListRecord(records);
        expectedModel.commitFinancialPlanner();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
