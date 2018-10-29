package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;

/**
 * Contains integration tests (interaction with the Model, UndoExpenseCommand and RedoExpenseCommand) and unit tests for
 * {@code DeleteExpenseCommand}.
 */
public class DeleteExpenseCommandTest {

    private Model model = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);

        String expectedMessage = String.format(DeleteExpenseCommand.MESSAGE_DELETE_EXPENSE_SUCCESS, expenseToDelete);

        ModelManager expectedModel = new ModelManager(new AddressBook(), model.getExpenseBook(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitExpenseBook();

        assertCommandSuccess(deleteExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(outOfBoundIndex);

        assertCommandFailure(deleteExpenseCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Expense expenseToDelete = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);
        Model expectedModel = new ModelManager(new AddressBook(), model.getExpenseBook(), new UserPrefs());
        expectedModel.deleteExpense(expenseToDelete);
        expectedModel.commitExpenseBook();

        // delete -> first expense deleted
        deleteExpenseCommand.execute(model, commandHistory);

        // undo -> reverts expensebook back to previous state and filtered expense list to show all expenses
        expectedModel.undoExpenseBook();
        assertCommandSuccess(new UndoExpenseCommand(), model, commandHistory,
                UndoExpenseCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first expense deleted again
        expectedModel.redoExpenseBook();
        assertCommandSuccess(new RedoExpenseCommand(), model, commandHistory,
                RedoExpenseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        DeleteExpenseCommand deleteExpenseCommand = new DeleteExpenseCommand(outOfBoundIndex);

        // execution failed -> expense book state not added into model
        assertCommandFailure(deleteExpenseCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);

        // single expense book state in model -> undoExpenseCommand and redoExpenseCommand fail
        assertCommandFailure(new UndoExpenseCommand(), model, commandHistory, UndoExpenseCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoExpenseCommand(), model, commandHistory, RedoExpenseCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        DeleteExpenseCommand deleteFirstCommand = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);
        DeleteExpenseCommand deleteSecondCommand = new DeleteExpenseCommand(INDEX_SECOND_EXPENSE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteExpenseCommand deleteFirstCommandCopy = new DeleteExpenseCommand(INDEX_FIRST_EXPENSE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
