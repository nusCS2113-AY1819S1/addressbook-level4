package seedu.planner.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.SortCommand.MESSAGE_SUCCESS;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Test;

import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.testutil.FinancialPlannerBuilder;

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
        assertNotEquals(sortCommandOne, null);
        assertNotEquals(sortCommandOne, "not_equal_string");
    }

    @Test
    public void execute_emptyList_listIsUnchanged() {
        SortCommand command = new SortCommand("name", true);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "ascending");

        assertCommandSuccess(command, emptyModel, commandHistory, expectedMessage, expectedEmptyModel);
    }

    @Test
    public void execute_unsortedList_listIsSorted() {
        SortCommand command = new SortCommand(SortCommand.CATEGORY_DATE, false);
        String expectedMessage = String.format(MESSAGE_SUCCESS, SortCommand.CATEGORY_DATE,
                SortCommand.ORDER_DESCENDING);
        // sort the initial model
        Model expectedSortedModel = getSortedModel(model, SortCommand.CATEGORY_DATE, SortCommand.ORDER_DESCENDING);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedSortedModel);
    }

    @Test
    public void execute_sortedList_listIsUnchanged() {
        SortCommand command = new SortCommand(SortCommand.CATEGORY_MONEY, true);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, SortCommand.CATEGORY_MONEY,
                SortCommand.ORDER_ASCENDING);
        // obtain a sorted model
        Model initialSortedModel = getSortedModel(model, SortCommand.CATEGORY_MONEY, SortCommand.ORDER_ASCENDING);
        /// try to sorted it again, result should be identical model
        Model expectedModel = getSortedModel(initialSortedModel, SortCommand.CATEGORY_MONEY,
                SortCommand.ORDER_ASCENDING);

        assertCommandSuccess(command, initialSortedModel, commandHistory, expectedMessage, expectedModel);
    }

    public Model getSortedModel(Model preSortedModel, String category, String order) {
        preSortedModel.sortFilteredRecordList(category, (order == SortCommand.ORDER_ASCENDING));
        return model;
    }
}
