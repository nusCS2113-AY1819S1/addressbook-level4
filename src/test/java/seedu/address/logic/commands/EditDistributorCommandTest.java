package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AHLEE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AHSENG;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIST_NAME_AHHUAT;
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
import seedu.address.logic.commands.EditDistributorCommand.EditDistributorDescriptor;
import seedu.address.model.DistributorBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ProductDatabase;
import seedu.address.model.TestStorage;
import seedu.address.model.UserDatabase;
import seedu.address.model.UserPrefs;
import seedu.address.model.distributor.Distributor;
import seedu.address.testutil.DistributorBuilder;
import seedu.address.testutil.EditDistributorDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 * @author Denise
 */
public class EditDistributorCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalDistributorBook(), new UserPrefs(),
            new UserDatabase(), new TestStorage());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Distributor editedDistributor = new DistributorBuilder().build();
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder(editedDistributor).build();
        EditDistributorCommand editDistributorCommand = new EditDistributorCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditDistributorCommand.MESSAGE_EDIT_DISTRIBUTOR_SUCCESS,
                editedDistributor);

        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateDistributor(model.getFilteredDistributorList().get(0), editedDistributor);
        expectedModel.commitDistributorBook();

        assertCommandSuccess(editDistributorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditDistributorCommand editDistributorCommand = new EditDistributorCommand(INDEX_FIRST,
                new EditDistributorDescriptor());
        Distributor editedDistributor =
                model.getFilteredDistributorList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(EditDistributorCommand.MESSAGE_EDIT_DISTRIBUTOR_SUCCESS,
                editedDistributor);

        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.commitDistributorBook();

        assertCommandSuccess(editDistributorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showDistributorAtIndex(model, INDEX_FIRST);

        Distributor distributorInFilteredList = model.getFilteredDistributorList()
                .get(INDEX_FIRST.getZeroBased());
        Distributor editedDistributor = new DistributorBuilder(distributorInFilteredList)
                .withName(VALID_DIST_NAME_AHHUAT).build();
        EditDistributorCommand editDistributorCommand = new EditDistributorCommand(INDEX_FIRST,
                new EditDistributorDescriptorBuilder().withName(VALID_DIST_NAME_AHHUAT).build());

        String expectedMessage =
                String.format(EditDistributorCommand.MESSAGE_EDIT_DISTRIBUTOR_SUCCESS, editedDistributor);

        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateDistributor(model.getFilteredDistributorList().get(0), editedDistributor);
        expectedModel.commitDistributorBook();

        assertCommandSuccess(editDistributorCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Distributor firstDistributor = model.getFilteredDistributorList().get(INDEX_FIRST.getZeroBased());
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder(firstDistributor).build();
        EditDistributorCommand editDistributorCommand = new EditDistributorCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editDistributorCommand, model, commandHistory,
                EditDistributorCommand.MESSAGE_DUPLICATE_DISTRIBUTOR);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showDistributorAtIndex(model, INDEX_FIRST);

        // edit product in filtered list into a duplicate in address book
        Distributor distributorInList = model.getDistributorInfoBook().getDistributorList()
                .get(INDEX_SECOND.getZeroBased());
        EditDistributorCommand editDistributorCommand = new EditDistributorCommand(INDEX_FIRST,
                new EditDistributorDescriptorBuilder(distributorInList).build());

        assertCommandFailure(editDistributorCommand, model, commandHistory,
                EditDistributorCommand.MESSAGE_DUPLICATE_DISTRIBUTOR);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDistributorList().size() + 1);
        EditDistributorDescriptor descriptor =
                new EditDistributorDescriptorBuilder().withName(VALID_DIST_NAME_AHHUAT).build();
        EditDistributorCommand editDistributorCommand = new EditDistributorCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editDistributorCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DIST_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Distributor editedDistributor = new DistributorBuilder().build();
        Distributor distributorToEdit = model.getFilteredDistributorList().get(INDEX_FIRST.getZeroBased());
        EditDistributorDescriptor descriptor = new EditDistributorDescriptorBuilder(editedDistributor).build();
        EditDistributorCommand editDistributorCommand = new EditDistributorCommand(INDEX_FIRST, descriptor);
        Model expectedModel = new ModelManager(new ProductDatabase(model.getProductInfoBook()),
                new DistributorBook(model.getDistributorInfoBook()), new UserPrefs(),
                new UserDatabase(), new TestStorage());
        expectedModel.updateDistributor(distributorToEdit, editedDistributor);
        expectedModel.commitDistributorBook();

        // edit -> first product edited
        editDistributorCommand.execute(model, commandHistory);

        // undo -> reverts distributorbook back to previous state and filtered distributor list to show all distributors
        expectedModel.undoDistributorBook();
        assertCommandSuccess(new UndoDistributorCommand(), model, commandHistory,
                UndoDistributorCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first product edited again
        expectedModel.redoDistributorBook();
        assertCommandSuccess(new RedoDistributorCommand(), model, commandHistory,
                RedoDistributorCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredDistributorList().size() + 1);
        EditDistributorDescriptor descriptor =
                new EditDistributorDescriptorBuilder().withName(VALID_DIST_NAME_AHHUAT).build();
        EditDistributorCommand editDistributorCommand = new EditDistributorCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editDistributorCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_DIST_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoDistributorCommand(), model, commandHistory,
                UndoDistributorCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoDistributorCommand(), model, commandHistory,
                RedoDistributorCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final EditDistributorCommand standardCommand = new EditDistributorCommand(INDEX_FIRST, DESC_AHSENG);

        // same values -> returns true
        EditDistributorDescriptor copyDescriptor = new EditDistributorDescriptor(DESC_AHSENG);
        EditDistributorCommand commandWithSameValues = new EditDistributorCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditDistributorCommand(INDEX_SECOND, DESC_AHSENG)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditDistributorCommand(INDEX_FIRST, DESC_AHLEE)));
    }

}
