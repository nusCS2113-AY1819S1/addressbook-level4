package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showDistributorAtIndex;
import static seedu.address.testutil.TypicalDistributors.getTypicalDistributorBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.DistributorBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProductDatabase;
import seedu.address.model.TestStorage;
import seedu.address.model.UserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.distributor.Distributor;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteDistributorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
            new UserDatabase(), new TestStorage());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Distributor distributorToDelete = model.getFilteredDistributorList().get(INDEX_FIRST.getZeroBased());
        DeleteDistributorCommand deleteDistributorCommand = new DeleteDistributorCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteDistributorCommand.MESSAGE_DELETE_DISTRIBUTOR_SUCCESS, distributorToDelete);

        ModelManager expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.deleteDistributor(distributorToDelete);
        expectedModel.commitDistributorBook();

        assertCommandSuccess(deleteDistributorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDistributorList().size() + 1);
        DeleteDistributorCommand deleteDistributorCommand = new DeleteDistributorCommand(outOfBoundIndex);

        assertCommandFailure(deleteDistributorCommand, model, commandHistory, Messages.MESSAGE_INVALID_DIST_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Distributor distributorToDelete = model.getFilteredDistributorList().get(INDEX_FIRST.getZeroBased());
        DeleteDistributorCommand deleteDistributorCommand = new DeleteDistributorCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.deleteDistributor(distributorToDelete);
        expectedModel.commitDistributorBook();

        // delete -> first person deleted
        deleteDistributorCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoDistributorBook();
        assertCommandSuccess(new UndoDistributorCommand(), model, commandHistory, UndoDistributorCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoDistributorBook();
        assertCommandSuccess(new RedoDistributorCommand(), model, commandHistory, RedoDistributorCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDistributorList().size() + 1);
        DeleteDistributorCommand deleteDistributorCommand = new DeleteDistributorCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteDistributorCommand, model, commandHistory, Messages.MESSAGE_INVALID_DIST_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoDistributorCommand(), model, commandHistory, UndoDistributorCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoDistributorCommand(), model, commandHistory, RedoDistributorCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteDistributorCommand deleteDistributorCommand = new DeleteDistributorCommand(INDEX_FIRST);
        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());

        showDistributorAtIndex(model, INDEX_SECOND);
        Distributor distributorToDelete = model.getFilteredDistributorList().get(INDEX_FIRST.getZeroBased());
        expectedModel.deleteDistributor(distributorToDelete);
        expectedModel.commitDistributorBook();

        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteDistributorCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoDistributorBook();
        assertCommandSuccess(new UndoDistributorCommand(), model, commandHistory, UndoDistributorCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> deletes same second person in unfiltered person list
        expectedModel.redoDistributorBook();
        assertCommandSuccess(new RedoDistributorCommand(), model, commandHistory, RedoDistributorCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteDistributorCommand deleteFirstCommand = new DeleteDistributorCommand(INDEX_FIRST);
        DeleteDistributorCommand deleteSecondCommand = new DeleteDistributorCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteDistributorCommand deleteFirstCommandCopy = new DeleteDistributorCommand(INDEX_FIRST);
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
    private void showNoDistributor(Model model) {
        model.updateFilteredDistributorList(p -> false);

        assertTrue(model.getFilteredDistributorList().isEmpty());
    }
}
