package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TestStorage;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteProductCommandTest {

    private Model model = new ModelManager(new TestStorage());
    private CommandHistory commandHistory = new CommandHistory();


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProductList().size() + 1);
        DeleteProductCommand deleteCommand = new DeleteProductCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);
    }


    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredProductList().size() + 1);
        DeleteProductCommand deleteCommand = new DeleteProductCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PRODUCT_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoProductCommand(), model, commandHistory, UndoProductCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoProductCommand(), model, commandHistory, RedoProductCommand.MESSAGE_FAILURE);
    }


    @Test
    public void equals() {
        DeleteProductCommand deleteFirstCommand = new DeleteProductCommand(INDEX_FIRST);
        DeleteProductCommand deleteSecondCommand = new DeleteProductCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteProductCommand deleteFirstCommandCopy = new DeleteProductCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredProductList(p -> false);

        assertTrue(model.getFilteredProductList().isEmpty());
    }
}
