package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.testutil.TypicalRecords.CAIFAN;
import static seedu.planner.testutil.TypicalRecords.CHICKENRICE;
import static seedu.planner.testutil.TypicalRecords.MALA;
import static seedu.planner.testutil.TypicalRecords.WORK;
import static seedu.planner.testutil.TypicalRecords.ZT;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;

public class ExportExcelCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        DateIsWithinIntervalPredicate firstPredicate = new DateIsWithinIntervalPredicate("26-09-2018", "27-09-2018");
        DateIsWithinIntervalPredicate firstPredicateCopy = new DateIsWithinIntervalPredicate("26-9-2018", "27-9-2018");
        DateIsWithinIntervalPredicate secondPredicate = new DateIsWithinIntervalPredicate("15-06-1987", "15-06-2019");

        ExportExcelCommand firstExportExcelCommand = new ExportExcelCommand(firstPredicate);
        ExportExcelCommand secondExportExcelCommand = new ExportExcelCommand(secondPredicate);

        // same object -> returns true
        Assert.assertTrue(firstPredicate.equals(firstPredicate));

        // same value expression -> returns true
        Assert.assertTrue(firstPredicate.equals(firstPredicateCopy));

        // same value -> returns true
        ExportExcelCommand firstExportExcelCommandCopy = new ExportExcelCommand(firstPredicate);
        Assert.assertTrue(firstExportExcelCommand.equals(firstExportExcelCommandCopy));

        //different value -> returns false
        Assert.assertFalse(firstExportExcelCommand.equals(secondExportExcelCommand));

        // different types -> returns false
        Assert.assertFalse(firstPredicate.equals(Arrays.asList("31-03-1999", "31-3-2019")));

        // null -> returns false
        Assert.assertFalse(firstPredicate.equals(null));

        // different predicate -> return false
        Assert.assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void execute_zeroRecordFound_noRecordFound() {
        DateIsWithinIntervalPredicate predicate = preparePredicate("31-3-1999", "31-03-1999");
        String nameFile = String.format("Financial_Planner_%1$s_%2$s.xlsx",
                predicate.getStartDate().getValue(), predicate.getEndDate().getValue());
        String expectedMessage = String.format(Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, nameFile);
        ExportExcelCommand command = new ExportExcelCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleRecordsFound() {
        DateIsWithinIntervalPredicate predicate = preparePredicate("26-09-1999", "27-9-2019");
        String nameFile = String.format("Financial_Planner_%1$s_%2$s.xlsx",
                predicate.getStartDate().getValue(), predicate.getEndDate().getValue());
        String expectedMessage = String.format(Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, nameFile);
        ExportExcelCommand command = new ExportExcelCommand(predicate);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredRecordList(), model.getFilteredRecordList());
    }

    /**
     * Parses {@code userInput} into a {@code DateIsWithinIntervalPredicate}.
     */
    private DateIsWithinIntervalPredicate preparePredicate(String startDate, String endDate) {
        return new DateIsWithinIntervalPredicate(startDate, endDate);
    }
}
