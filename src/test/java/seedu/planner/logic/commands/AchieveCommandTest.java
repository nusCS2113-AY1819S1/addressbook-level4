package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.model.DirectoryPath.HOME_DIRECTORY_STRING;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.LogsCenter;
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

public class AchieveCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private Logger logger = LogsCenter.getLogger(AchieveCommand.class);

    @Test
    public void equals() {
        Date startDate1 = TypicalRecords.TYPICAL_START_DATE1;
        Date startDate2 = TypicalRecords.TYPICAL_START_DATE1;
        Date endDate1 = TypicalRecords.TYPICAL_END_DATE;
        Date endDate2 = TypicalRecords.TYPICAL_END_DATE1;
        String directoryPath = HOME_DIRECTORY_STRING;
        String directoryPath1 = DirectoryPath.WORKING_DIRECTORY_STRING;

        AchieveCommand achieveCommand11 = new AchieveCommand();
        AchieveCommand achieveCommand21 = new AchieveCommand(directoryPath);
        AchieveCommand achieveCommand31 = new AchieveCommand(startDate1, endDate1);
        AchieveCommand achieveCommand41 = new AchieveCommand(startDate1, endDate1, directoryPath);

        AchieveCommand achieveCommand22 = new AchieveCommand(directoryPath1);
        AchieveCommand achieveCommand32 = new AchieveCommand(startDate2, endDate2);
        AchieveCommand achieveCommand42 = new AchieveCommand(startDate2, endDate2, directoryPath1);

        // same object -> returns true
        Assert.assertTrue(achieveCommand11.equals(achieveCommand11));
        Assert.assertTrue(achieveCommand21.equals(achieveCommand21));
        Assert.assertTrue(achieveCommand31.equals(achieveCommand31));
        Assert.assertTrue(achieveCommand41.equals(achieveCommand41));

        // same value -> returns true
        AchieveCommand achieveCommand11Copy = new AchieveCommand();
        AchieveCommand achieveExcelCommand21Copy = new AchieveCommand(directoryPath);
        AchieveCommand achieveExcelCommand31Copy = new AchieveCommand(startDate1, endDate1);
        AchieveCommand achieveExcelCommand41Copy = new AchieveCommand(startDate1, endDate1, directoryPath);

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
        AchieveCommand command = new AchieveCommand(
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
        AchieveCommand command = new AchieveCommand(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE, HOME_DIRECTORY_STRING);
        expectedModel.updateFilteredRecordList(new DateIsWithinIntervalPredicate(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE));
        List<Record> records = expectedModel.getFilteredRecordList();
        expectedModel.deleteListRecord(records);
        expectedModel.commitFinancialPlanner();
        logger.info("3. The size of the record list: " + records.size());
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
