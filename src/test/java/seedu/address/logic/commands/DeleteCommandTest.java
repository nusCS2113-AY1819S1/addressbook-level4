package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showBookAtIndex;
import static seedu.address.testutil.TypicalArgTypes.ARGS_INDEX;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE_BASED_FIRST_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_ONE_BASED_SECOND_BOOK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BOOK;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.book.Book;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalBookInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_ONE_BASED_FIRST_BOOK, ARGS_INDEX);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete);

        ModelManager expectedModel = new ModelManager(model.getBookInventory(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookInventory();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(Integer.toString(outOfBoundIndex.getOneBased()) , ARGS_INDEX);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_ONE_BASED_FIRST_BOOK, ARGS_INDEX);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete);

        Model expectedModel = new ModelManager(model.getBookInventory(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookInventory();
        showNoBook(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showBookAtIndex(model, INDEX_FIRST_BOOK);

        Index outOfBoundIndex = INDEX_SECOND_BOOK;
        // ensures that outOfBoundIndex is still in bounds of BookInventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getBookInventory().getBookList().size());

        DeleteCommand deleteCommand = new DeleteCommand(Integer.toString(outOfBoundIndex.getOneBased()) , ARGS_INDEX);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_ONE_BASED_FIRST_BOOK, ARGS_INDEX);
        Model expectedModel = new ModelManager(model.getBookInventory(), new UserPrefs());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookInventory();

        // delete -> first book deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered book list to show all persons
        expectedModel.undoBookInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first book deleted again
        expectedModel.redoBookInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredBookList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(Integer.toString(outOfBoundIndex.getOneBased()) , ARGS_INDEX);

        // execution failed -> BookInventory state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);

        // single BookInventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Book} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted book in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the book object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameBookDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_ONE_BASED_FIRST_BOOK, ARGS_INDEX);
        Model expectedModel = new ModelManager(model.getBookInventory(), new UserPrefs());

        showBookAtIndex(model, INDEX_SECOND_BOOK);
        Book bookToDelete = model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased());
        expectedModel.deleteBook(bookToDelete);
        expectedModel.commitBookInventory();

        // delete -> deletes second book in unfiltered book list / first book in filtered book list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts inventory book back to previous state and filtered book list to show all books
        expectedModel.undoBookInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(bookToDelete, model.getFilteredBookList().get(INDEX_FIRST_BOOK.getZeroBased()));
        // redo -> deletes same second book in unfiltered book list
        expectedModel.redoBookInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_ONE_BASED_FIRST_BOOK, ARGS_INDEX);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_ONE_BASED_SECOND_BOOK, ARGS_INDEX);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_ONE_BASED_FIRST_BOOK, ARGS_INDEX);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different book -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBook(Model model) {
        model.updateFilteredBookList(p -> false);

        assertTrue(model.getFilteredBookList().isEmpty());
    }
}
