package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.SortCommand.*;
import static seedu.planner.testutil.TypicalRecords.JAP;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Record;
import seedu.planner.testutil.FinancialPlannerBuilder;
import seedu.planner.testutil.RecordBuilder;

public class SortCommandTest {

    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private Model emptyModel = new ModelManager(new FinancialPlannerBuilder().build(), new UserPrefs());
    private Model expectedEmptyModel = new ModelManager(new FinancialPlannerBuilder().build(), new UserPrefs());

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        SortCommand sortCommandOne = new SortCommand(SortCommand.CATEGORY_NAME, true);
        SortCommand sortCommandTwo = new SortCommand(SortCommand.CATEGORY_MONEY, false);
        SortCommand sortCommandOneCopy = new SortCommand(SortCommand.CATEGORY_NAME, true);
        SortCommand sortCommandDateTrue = new SortCommand(SortCommand.CATEGORY_DATE, true);
        SortCommand sortCommandNameFalse = new SortCommand(SortCommand.CATEGORY_NAME, false);

        // same object -> returns true
        assertEquals(sortCommandOne, sortCommandOne);
        // same values -> returns true
        assertEquals(sortCommandOne, sortCommandOneCopy);

        // different values -> return false
        assertNotEquals(sortCommandOne, sortCommandDateTrue);
        assertNotEquals(sortCommandOne, sortCommandNameFalse);
        assertNotEquals(sortCommandOne, sortCommandTwo);

        // different kind of objects -> return false
        assertNotEquals(sortCommandOne,null);
        assertNotEquals(sortCommandOne, "not_equal_string");

    }

    @Test
    public void execute_emptyList_listIsUnchanged() {
        SortCommand command = new SortCommand("name", true);
        String expectedMessage = String.format(MESSAGE_SUCCESS,  "name", "ascending");
        assertCommandSuccess(command, emptyModel, commandHistory, expectedMessage, expectedEmptyModel);
    }

    @Test
    public void execute_listIsNotSorted_listIsSorted() {
        String category = CATEGORY_DATE;
        String order = ORDER_DESCENDING;
        SortCommand command = new SortCommand(category, false);
        String expectedMessage = String.format(MESSAGE_SUCCESS, category, order);
        Model expectedSortedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        expectedSortedModel.sortFilteredRecordList(category, false);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedSortedModel);
    }

    @Test
    public void execute_listIsSorted_listIsUnchanged() {
        String category = CATEGORY_MONEY;
        String order = ORDER_ASCENDING;
        SortCommand command = new SortCommand(category, true);
        String expectedMessage = String.format(MESSAGE_SUCCESS, category, order);
        Model expectedSortedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        expectedSortedModel.sortFilteredRecordList(category, true);
        assertCommandSuccess(command, expectedSortedModel, commandHistory, expectedMessage, expectedSortedModel);
    }

    @Test
    public void execute_filteredListIsSorted_listIsUnchanged() {
        String category = CATEGORY_NAME;
        String order = ORDER_DESCENDING;
        SortCommand command = new SortCommand(category, false);
        String expectedMessage = String.format(MESSAGE_SUCCESS,  category, order);
        Model expectedSortedModel = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
        expectedSortedModel.sortFilteredRecordList(category, false);
        assertCommandSuccess(command, expectedSortedModel, commandHistory, expectedMessage, expectedSortedModel);
    }

}
