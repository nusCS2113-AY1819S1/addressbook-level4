package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpensesAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSES;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;
import static seedu.address.testutil.expenses.TypicalExpenses.getTypicalExpensesList;
import static seedu.address.testutil.schedule.TypicalSchedules.getTypicalScheduleList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expenses.Expenses;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class RemoveExpensesCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalExpensesList(), getTypicalScheduleList(),
            getTypicalRecruitmentList(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Expenses expensesToDelete = model.getFilteredExpensesList().get(INDEX_FIRST_EXPENSES.getZeroBased());
        RemoveExpensesCommand removeExpensesCommand = new RemoveExpensesCommand(INDEX_FIRST_EXPENSES);

        String expectedMessage = String.format(
                RemoveExpensesCommand.MESSAGE_REMOVE_EXPENSES_SUCCESS, expensesToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        expectedModel.deleteExpenses(expensesToDelete);
        expectedModel.commitExpensesList();

        try {
            assertCommandSuccess(removeExpensesCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveExpensesCommand removeExpensesCommand = new RemoveExpensesCommand(outOfBoundIndex);

        assertCommandFailure(removeExpensesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSES_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showExpensesAtIndex(model, INDEX_FIRST_EXPENSES);
        Expenses expensesToDelete = model.getFilteredExpensesList().get(INDEX_FIRST_EXPENSES.getZeroBased());
        RemoveExpensesCommand removeExpensesCommand = new RemoveExpensesCommand(INDEX_FIRST_EXPENSES);

        String expectedMessage = String.format(
                RemoveExpensesCommand.MESSAGE_REMOVE_EXPENSES_SUCCESS, expensesToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(),
                model.getScheduleList(), model.getRecruitmentList(), new UserPrefs());

        expectedModel.deleteExpenses(expensesToDelete);
        expectedModel.commitExpensesList();
        showNoExpenses(expectedModel);

        try {
            assertCommandSuccess(removeExpensesCommand, model, commandHistory, expectedMessage, expectedModel);
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showExpensesAtIndex(model, INDEX_FIRST_EXPENSES);

        Index outOfBoundIndex = INDEX_SECOND_EXPENSES;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getExpensesList().getExpensesRequestList().size());

        RemoveExpensesCommand removeExpensesCommand = new RemoveExpensesCommand(outOfBoundIndex);

        assertCommandFailure(removeExpensesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSES_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Expenses expensesToDelete = model.getFilteredExpensesList().get(INDEX_FIRST_EXPENSES.getZeroBased());
        RemoveExpensesCommand removeExpensesCommand = new RemoveExpensesCommand(INDEX_FIRST_EXPENSES);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());
        expectedModel.deleteExpenses(expensesToDelete);
        expectedModel.commitExpensesList();

        // delete -> first expenses deleted
        removeExpensesCommand.execute(model, commandHistory);

        // undo -> reverts expenses list back to previous state and filtered expenses list to show all expenses
        UndoCommand undoCommand = new UndoCommand();
        undoCommand.execute(expectedModel, commandHistory);
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        RedoCommand redoCommand = new RedoCommand();
        redoCommand.execute(expectedModel, commandHistory);
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpensesList().size() + 1);
        RemoveExpensesCommand removeExpensesCommand = new RemoveExpensesCommand(outOfBoundIndex);

        // execution failed -> expenses list state not added into model
        assertCommandFailure(removeExpensesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSES_DISPLAYED_INDEX);

        // single expenses list state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Expenses} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted expenses in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        RemoveExpensesCommand removeExpensesCommand = new RemoveExpensesCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getExpensesList(), model.getScheduleList(),
                model.getRecruitmentList(), new UserPrefs());

        showExpensesAtIndex(model, INDEX_SECOND_EXPENSES);
        Expenses expensesToDelete = model.getFilteredExpensesList().get(INDEX_FIRST_EXPENSES.getZeroBased());

        expectedModel.deleteExpenses(expensesToDelete);

        expectedModel.commitExpensesList();
        expectedModel.updateFilteredExpensesList(Model.PREDICATE_SHOW_ALL_EXPENSES);

        // delete -> deletes second expenses in unfiltered person list / first person in filtered expenses list
        removeExpensesCommand.execute(model, commandHistory);

        // undo -> reverts expenses list back to previous state and filtered person list to show all expenses
        expectedModel.undoExpensesList();
        expectedModel.undoModelList();

        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(expensesToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second expenses in unfiltered person list
        expectedModel.redoExpensesList();
        expectedModel.redoModelList();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoExpenses(Model model) {
        model.updateFilteredExpensesList(unused -> false);

        assertTrue(model.getFilteredExpensesList().isEmpty());
    }

    @Test
    public void equals() {
        RemoveExpensesCommand deleteFirstCommand = new RemoveExpensesCommand(INDEX_FIRST_PERSON);
        RemoveExpensesCommand deleteSecondCommand = new RemoveExpensesCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        RemoveExpensesCommand deleteFirstCommandCopy = new RemoveExpensesCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
