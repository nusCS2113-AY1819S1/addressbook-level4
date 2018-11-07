package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.model.DirectoryPath.WORKING_DIRECTORY_STRING;
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
public class ArchiveCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Date startDate1 = TypicalRecords.TYPICAL_START_DATE1;
        Date startDate2 = TypicalRecords.TYPICAL_START_DATE1;
        Date endDate1 = TypicalRecords.TYPICAL_END_DATE;
        Date endDate2 = TypicalRecords.TYPICAL_END_DATE1;
        String directoryPath = WORKING_DIRECTORY_STRING;
        String directoryPath1 = DirectoryPath.HOME_DIRECTORY_STRING;

        ArchiveCommand archiveCommand11 = new ArchiveCommand();
        ArchiveCommand archiveCommand21 = new ArchiveCommand(directoryPath);
        ArchiveCommand archiveCommand31 = new ArchiveCommand(startDate1, endDate1);
        ArchiveCommand archiveCommand41 = new ArchiveCommand(startDate1, endDate1, directoryPath);

        ArchiveCommand archiveCommand32 = new ArchiveCommand(startDate2, endDate2);
        ArchiveCommand archiveCommand42 = new ArchiveCommand(startDate2, endDate2, directoryPath1);

        // same object -> returns true
        Assert.assertTrue(archiveCommand11.equals(archiveCommand11));
        Assert.assertTrue(archiveCommand21.equals(archiveCommand21));
        Assert.assertTrue(archiveCommand31.equals(archiveCommand31));
        Assert.assertTrue(archiveCommand41.equals(archiveCommand41));

        // same value -> returns true
        ArchiveCommand archiveCommand11Copy = new ArchiveCommand();
        ArchiveCommand archiveCommand21Copy = new ArchiveCommand(directoryPath);
        ArchiveCommand archiveCommand31Copy = new ArchiveCommand(startDate1, endDate1);
        ArchiveCommand archiveCommand41Copy = new ArchiveCommand(startDate1, endDate1, directoryPath);

        Assert.assertTrue(archiveCommand11.equals(archiveCommand11Copy));
        Assert.assertTrue(archiveCommand21.equals(archiveCommand21Copy));
        Assert.assertTrue(archiveCommand31.equals(archiveCommand31Copy));
        Assert.assertTrue(archiveCommand41.equals(archiveCommand41Copy));

        //different value -> returns false
        Assert.assertFalse(archiveCommand31.equals(archiveCommand32));
        Assert.assertFalse(archiveCommand41.equals(archiveCommand42));

        // different types -> returns false
        Assert.assertFalse(archiveCommand41.equals(Arrays.asList(startDate1, endDate1, directoryPath)));

        // null -> returns false
        Assert.assertFalse(archiveCommand11.equals(null));
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
                    nameFile, DirectoryPath.WORKING_DIRECTORY_STRING);
            expectedModel.deleteListRecord(records);
            expectedModel.commitFinancialPlanner();
        } else {
            expectedMessage = Messages.MESSAGE_ARCHIVE_COMMAND_ERRORS;
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
                Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY + Messages.MESSAGE_ARCHIVE_SUCCESSFULLY,
                nameFile, WORKING_DIRECTORY_STRING);
        ArchiveCommand command = new ArchiveCommand(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE, WORKING_DIRECTORY_STRING);
        expectedModel.updateFilteredRecordList(new DateIsWithinIntervalPredicate(
                TypicalRecords.TYPICAL_START_FAR_DATE, TypicalRecords.TYPICAL_END_FAR_DATE));
        List<Record> records = expectedModel.getFilteredRecordList();
        expectedModel.deleteListRecord(records);
        expectedModel.commitFinancialPlanner();
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }
}
