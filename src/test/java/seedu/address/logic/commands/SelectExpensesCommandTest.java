package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpensesAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSES;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSES;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EXPENSES;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListExpensesRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectExpensesCommand}.
 */
public class SelectExpensesCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(),
            getTypicalScheduleList(), getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastExpensesIndex = Index.fromOneBased(model.getFilteredExpensesList().size());

        assertExecutionSuccess(INDEX_FIRST_EXPENSES);
        assertExecutionSuccess(INDEX_THIRD_EXPENSES);
        assertExecutionSuccess(lastExpensesIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredExpensesList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_EXPENSES_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showExpensesAtIndex(model, INDEX_FIRST_EXPENSES);
        showExpensesAtIndex(expectedModel, INDEX_FIRST_EXPENSES);

        assertExecutionSuccess(INDEX_FIRST_EXPENSES);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showExpensesAtIndex(model, INDEX_FIRST_EXPENSES);
        showExpensesAtIndex(expectedModel, INDEX_FIRST_EXPENSES);

        Index outOfBoundsIndex = INDEX_SECOND_EXPENSES;
        // ensures that outOfBoundIndex is still in bounds of expenses List list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getExpensesList().getExpensesRequestList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_EXPENSES_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectExpensesCommand selectFirstCommand = new SelectExpensesCommand(INDEX_FIRST_EXPENSES);
        SelectExpensesCommand selectSecondCommand = new SelectExpensesCommand(INDEX_SECOND_EXPENSES);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectExpensesCommand selectFirstCommandCopy = new SelectExpensesCommand(INDEX_FIRST_EXPENSES);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different expenses -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectExpensesCommand} with the given {@code index},
     * and checks that {@code JumpToListExpensesRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectExpensesCommand selectExpensesCommand = new SelectExpensesCommand(index);
        String expectedMessage = String.format(
                SelectExpensesCommand.MESSAGE_SELECT_EXPENSES_SUCCESS, index.getOneBased());

        try {
            assertCommandSuccess(selectExpensesCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        JumpToListExpensesRequestEvent lastEvent =
                (JumpToListExpensesRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectExpensesCommand} with the given {@code index},
     * and checks that a {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectExpensesCommand selectExpensesCommand = new SelectExpensesCommand(index);
        assertCommandFailure(selectExpensesCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }

}
