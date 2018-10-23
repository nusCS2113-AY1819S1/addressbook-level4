package seedu.planner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.planner.testutil.TypicalRecords.TYPICAL_END_DATE;
import static seedu.planner.testutil.TypicalRecords.TYPICAL_START_DATE;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.ReadOnlyFinancialPlanner;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.DateIsWithinIntervalPredicate;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.FinancialPlannerBuilder;
import seedu.planner.testutil.TypicalRecords;

public class ExportExcelCommandTest {
    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());;
    ReadOnlyFinancialPlanner emptyFinancialPlanner = new FinancialPlannerBuilder().build();
    private Model emptyModel = new ModelManager(emptyFinancialPlanner, new UserPrefs());
    private Model expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
    private Model expectedEmptyModel = emptyModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exportExcelIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ExportExcelCommand(), model, commandHistory,
                Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, expectedModel);
    }

    @Test
    public void execute_exportExcelIsFilteredWithDefault_showsEverything() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        assertCommandSuccess(new ExportExcelCommand(),
                model, commandHistory, Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, expectedModel);
    }

    /**
     * Tests that the exportExcel shown after filtering according to date interval given is equal to the expected model
     * after the same filter
     */
    @Test
    public void execute_exportExcelIsFilteredWithDateIntervals_showsCorrectList() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        Model expectedModelAfterFilter = filterListWithSpecificDateInterval(
                expectedModel, TYPICAL_START_DATE, TYPICAL_END_DATE);
        assertCommandSuccess(new ExportExcelCommand(TYPICAL_START_DATE, TYPICAL_END_DATE), model, commandHistory,
                Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, expectedModelAfterFilter);
    }

    @Test
    public void execute_emptyListFiltered_showsCorrectList() {
        assertCommandSuccess(new ExportExcelCommand(TYPICAL_START_DATE, TYPICAL_END_DATE), emptyModel, commandHistory,
                Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY, expectedEmptyModel);
    }

    /**
     * Filters a model based on the predicate DateIsWithinIntervalPredicate
     * @param toFilter
     * @param startDate all dates must be later or equal to this date
     * @param endDate all dates must be earlier or equal to this date
     * @return Model
     */
    private Model filterListWithSpecificDateInterval(Model toFilter, Date startDate, Date endDate) {
        Predicate<Record> predicate = new DateIsWithinIntervalPredicate(startDate, endDate);
        toFilter.updateFilteredRecordList(predicate);
        Model filtered = toFilter;
        return filtered;
    }

    @Test
    public void equals() {
        ExportExcelCommand firstExportExcelCommandTwoDifferentDates = new ExportExcelCommand(
                TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_END_DATE);
        ExportExcelCommand secondExportExcelCommandTwoDifferentDates = new ExportExcelCommand(
                TypicalRecords.TYPICAL_START_DATE1, TypicalRecords.TYPICAL_END_DATE1);

        ExportExcelCommand firstExportExcelCommandTwoSameDates = new ExportExcelCommand(
                TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_START_DATE);
        ExportExcelCommand secondExportExcelCommandTwoSameDates = new ExportExcelCommand(
                TypicalRecords.TYPICAL_END_DATE, TypicalRecords.TYPICAL_END_DATE);

        ExportExcelCommand exportExcelCommandNullDates = new ExportExcelCommand(null, null);
        ExportExcelCommand exportExcelCommandNull = new ExportExcelCommand();

        // same object -> returns true
        Assert.assertTrue(firstExportExcelCommandTwoDifferentDates.equals(firstExportExcelCommandTwoDifferentDates));
        Assert.assertTrue(firstExportExcelCommandTwoSameDates.equals(firstExportExcelCommandTwoSameDates));

        // same value -> returns true
        ExportExcelCommand firstExportExcelCommandTwoDifferentDatesCopy = new ExportExcelCommand(
                TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_END_DATE);
        ExportExcelCommand firstExportExcelCommandTwoSameDatesCopy = new ExportExcelCommand(
                TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_START_DATE);

        Assert.assertTrue(
                firstExportExcelCommandTwoDifferentDates.equals(firstExportExcelCommandTwoDifferentDatesCopy));
        Assert.assertTrue(
                firstExportExcelCommandTwoSameDates.equals(firstExportExcelCommandTwoSameDatesCopy));

        // null -> returns false, although give the same result later.
        Assert.assertFalse(exportExcelCommandNullDates.equals(exportExcelCommandNull));

        //different value dates -> returns false
        Assert.assertFalse(firstExportExcelCommandTwoDifferentDates.equals(secondExportExcelCommandTwoDifferentDates));
        Assert.assertFalse(firstExportExcelCommandTwoSameDates.equals(secondExportExcelCommandTwoSameDates));

        // different types -> returns false
        Assert.assertFalse(firstExportExcelCommandTwoDifferentDates.equals(
                Arrays.asList(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_END_DATE)));
        Assert.assertFalse(firstExportExcelCommandTwoSameDates.equals(
                Arrays.asList(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_START_DATE)));

    }


    @Test
    public void execute_zeroRecordFound_noRecordFound() {
        DateIsWithinIntervalPredicate predicate =
                preparePredicate(
                        TypicalRecords.TYPICAL_NONEXISTENT_STARTDATE, TypicalRecords.TYPICAL_NONEXISTENT_ENDDATE);
        String expectedMessage = String.format(Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY);
        ExportExcelCommand command = new ExportExcelCommand(
                TypicalRecords.TYPICAL_NONEXISTENT_STARTDATE, TypicalRecords.TYPICAL_NONEXISTENT_ENDDATE);
        expectedModel.updateFilteredRecordList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRecordList());
    }

    @Test
    public void execute_multipleRecordsFound() {
        String expectedMessage = String.format(Messages.MESSAGE_EXCEL_FILE_WRITTEN_SUCCESSFULLY);
        ExportExcelCommand command = new ExportExcelCommand(
                TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_END_DATE);
        expectedModel.updateFilteredRecordList(
                preparePredicate(TypicalRecords.TYPICAL_START_DATE, TypicalRecords.TYPICAL_END_DATE));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredRecordList(), model.getFilteredRecordList());
    }

    /**
     * Parses {@code userInput} into a {@code DateIsWithinIntervalPredicate}.
     */
    private DateIsWithinIntervalPredicate preparePredicate(String startDate, String endDate) {
        return new DateIsWithinIntervalPredicate(startDate, endDate);
    }
    private DateIsWithinIntervalPredicate preparePredicate(Date startDate, Date endDate) {
        return new DateIsWithinIntervalPredicate(startDate, endDate);
    }
}
