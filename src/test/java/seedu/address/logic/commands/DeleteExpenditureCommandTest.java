package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showExpenditureAtIndex;
import static seedu.address.testutil.TypicalExpenditures.getTypicalExpenditureTracker;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EXPENDITURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EXPENDITURE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTodoList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteExpenditureCommand}.
 */
public class DeleteExpenditureCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTodoList(),
            getTypicalExpenditureTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidIndexUnfilteredListSuccess() {
        Expenditure expenditureToDelete =
                model.getFilteredExpenditureList().get(INDEX_FIRST_EXPENDITURE.getZeroBased());
        DeleteExpenditureCommand deleteExpenditureCommand = new DeleteExpenditureCommand(INDEX_FIRST_EXPENDITURE);

        String expectedMessage = String.format(DeleteExpenditureCommand.MESSAGE_DELETE_SUCCESS, expenditureToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.deleteExpenditure(expenditureToDelete);
        expectedModel.commitExpenditureList();

        assertCommandSuccess(deleteExpenditureCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenditureList().size() + 1);
        DeleteExpenditureCommand deleteExpenditureCommand = new DeleteExpenditureCommand(outOfBoundIndex);

        assertCommandFailure(deleteExpenditureCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
    }

    @Test
    public void executeValidIndexFilteredListSuccess() {
        showExpenditureAtIndex(model, INDEX_FIRST_EXPENDITURE);

        Expenditure expenditureToDelete =
                model.getFilteredExpenditureList().get(INDEX_FIRST_EXPENDITURE.getZeroBased());
        DeleteExpenditureCommand deleteExpenditureCommand = new DeleteExpenditureCommand(INDEX_FIRST_EXPENDITURE);

        String expectedMessage = String.format(DeleteExpenditureCommand.MESSAGE_DELETE_SUCCESS, expenditureToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.deleteExpenditure(expenditureToDelete);
        expectedModel.commitExpenditureList();
        showNoExpenditure(expectedModel);

        assertCommandSuccess(deleteExpenditureCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() {
        showExpenditureAtIndex(model, INDEX_FIRST_EXPENDITURE);

        Index outOfBoundIndex = INDEX_SECOND_EXPENDITURE;
        // ensures that outOfBoundIndex is still in bounds of expenditure tracker list
        assertTrue(outOfBoundIndex.getZeroBased()
                < model.getExpenditureTracker().getExpenditureList().size());

        DeleteExpenditureCommand deleteExpenditureCommand = new DeleteExpenditureCommand(outOfBoundIndex);

        assertCommandFailure(deleteExpenditureCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);
    }
    /*
    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        // delete -> first person deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    @Test
    public void executeUndoRedoInvalidIndexUnfilteredListFailure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredExpenditureList().size() + 1);
        DeleteExpenditureCommand deleteExpenditureCommand = new DeleteExpenditureCommand(outOfBoundIndex);

        // execution failed -> expenditure tracker state not added into model
        assertCommandFailure(deleteExpenditureCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_EXPENDITURE_DISPLAYED_INDEX);

        // single expenditure state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Expenditure} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted expenditure in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the expenditure object regardless of indexing.
     */
    /*
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), getTypicalTodoList(),
                getTypicalExpenditureTracker(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }*/

    @Test
    public void equals() {
        DeleteExpenditureCommand deleteFirstCommand = new DeleteExpenditureCommand(INDEX_FIRST_EXPENDITURE);
        DeleteExpenditureCommand deleteSecondCommand = new DeleteExpenditureCommand(INDEX_SECOND_EXPENDITURE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        /*
        DeleteExpenditureCommand deleteFirstCommandCopy = new DeleteExpenditureCommand(INDEX_FIRST_EXPENDITURE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));
        */

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different expenditure -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */

    private void showNoExpenditure(Model model) {
        model.updateFilteredExpenditureList(p -> false);

        assertTrue(model.getFilteredExpenditureList().isEmpty());
    }
}
