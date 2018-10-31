package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MRT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_CATEGORY_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPENSE_VALUE_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_TAOBAO;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalExpenses.getTypicalExpenseBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENSE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENSE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditExpenseCommand.EditExpenseDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ExpenseBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.expense.Expense;
import seedu.address.testutil.EditExpenseDescriptorBuilder;
import seedu.address.testutil.ExpenseBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditExpenseCommandTest {

    private Model model = new ModelManager(new AddressBook(), getTypicalExpenseBook(), new EventBook(),
            new TaskBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Expense editedExpense = new ExpenseBuilder().build();
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(INDEX_FIRST_EXPENSE, descriptor);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel =
                new ModelManager(new AddressBook(), new ExpenseBook(model.getExpenseBook()), new EventBook(),
                        new TaskBook(), new UserPrefs());
        expectedModel.updateExpense(model.getFilteredExpenseList().get(0), editedExpense);
        expectedModel.commitExpenseBook();

        assertCommandSuccess(editExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastExpense = Index.fromOneBased(model.getFilteredExpenseList().size());
        Expense lastExpense = model.getFilteredExpenseList().get(indexLastExpense.getZeroBased());

        ExpenseBuilder personInList = new ExpenseBuilder(lastExpense);
        Expense editedExpense = personInList.withExpenseCategory(VALID_EXPENSE_CATEGORY_SHOPPING)
                .withExpenseValue(VALID_EXPENSE_VALUE_SHOPPING)
                .withTags(VALID_TAG_TAOBAO)
                .build();

        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withExpenseCategory(VALID_EXPENSE_CATEGORY_SHOPPING)
                .withExpenseValue(VALID_EXPENSE_VALUE_SHOPPING)
                .withTags(VALID_TAG_TAOBAO)
                .build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(indexLastExpense, descriptor);

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel =
                new ModelManager(new AddressBook(), new ExpenseBook(model.getExpenseBook()), new EventBook(),
                        new TaskBook(), new UserPrefs());
        expectedModel.updateExpense(lastExpense, editedExpense);
        expectedModel.commitExpenseBook();

        assertCommandSuccess(editExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditExpenseCommand editExpenseCommand =
                new EditExpenseCommand(INDEX_FIRST_EXPENSE, new EditExpenseDescriptor());
        Expense editedExpense = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());

        String expectedMessage = String.format(EditExpenseCommand.MESSAGE_EDIT_EXPENSE_SUCCESS, editedExpense);

        Model expectedModel =
                new ModelManager(new AddressBook(), new ExpenseBook(model.getExpenseBook()), new EventBook(),
                        new TaskBook(), new UserPrefs());
        expectedModel.commitExpenseBook();

        assertCommandSuccess(editExpenseCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidExpenseIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder()
                .withExpenseCategory(VALID_EXPENSE_CATEGORY_SHOPPING)
                .build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editExpenseCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Expense editedExpense = new ExpenseBuilder().build();
        Expense expenseToEdit = model.getFilteredExpenseList().get(INDEX_FIRST_EXPENSE.getZeroBased());
        EditExpenseDescriptor descriptor = new EditExpenseDescriptorBuilder(editedExpense).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(INDEX_FIRST_EXPENSE, descriptor);
        Model expectedModel =
                new ModelManager(new AddressBook(), new ExpenseBook(model.getExpenseBook()), new EventBook(),
                        new TaskBook(), new UserPrefs());
        expectedModel.updateExpense(expenseToEdit, editedExpense);
        expectedModel.commitExpenseBook();

        // edit -> first expense edited
        editExpenseCommand.execute(model, commandHistory);

        // undo -> reverts expensebook back to previous state and filtered expense list to show all expenses
        expectedModel.undoExpenseBook();
        assertCommandSuccess(new UndoExpenseCommand(), model, commandHistory,
                UndoExpenseCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first expense edited again
        expectedModel.redoExpenseBook();
        assertCommandSuccess(new RedoExpenseCommand(), model, commandHistory,
                RedoExpenseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenseList().size() + 1);
        EditExpenseDescriptor descriptor =
                new EditExpenseDescriptorBuilder().withExpenseCategory(VALID_EXPENSE_CATEGORY_SHOPPING).build();
        EditExpenseCommand editExpenseCommand = new EditExpenseCommand(outOfBoundIndex, descriptor);

        // execution failed -> expense book state not added into model
        assertCommandFailure(editExpenseCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENSE_DISPLAYED_INDEX);

        // single expense book state in model -> undoExpenseCommand and redoExpenseCommand fail
        assertCommandFailure(new UndoExpenseCommand(), model, commandHistory, UndoExpenseCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoExpenseCommand(), model, commandHistory, RedoExpenseCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final EditExpenseCommand standardCommand = new EditExpenseCommand(INDEX_FIRST_EXPENSE, DESC_SHOPPING);

        // same values -> returns true
        EditExpenseDescriptor copyDescriptor = new EditExpenseDescriptor(DESC_SHOPPING);
        EditExpenseCommand commandWithSameValues = new EditExpenseCommand(INDEX_FIRST_EXPENSE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearExpenseCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditExpenseCommand(INDEX_SECOND_EXPENSE, DESC_SHOPPING)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditExpenseCommand(INDEX_FIRST_EXPENSE, DESC_MRT)));
    }

}
