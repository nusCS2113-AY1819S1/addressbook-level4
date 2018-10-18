package seedu.planner.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.planner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.planner.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.planner.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.planner.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.planner.testutil.TypicalRecords.getTypicalFinancialPlanner;

import org.junit.Ignore;
import org.junit.Test;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.CommandHistory;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.UserPrefs;
import seedu.planner.model.record.Record;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
@Ignore
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalFinancialPlanner(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Record recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECORD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RECORD_SUCCESS, recordToDelete);

        ModelManager expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitFinancialPlanner();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);

        Record recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECORD);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RECORD_SUCCESS, recordToDelete);

        Model expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitFinancialPlanner();
        showNoRecord(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);

        Index outOfBoundIndex = INDEX_SECOND_RECORD;
        // ensures that outOfBoundIndex is still in bounds of planner book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFinancialPlanner().getRecordList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Record recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECORD);
        Model expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitFinancialPlanner();

        // delete -> first record deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts financialplanner back to previous state and filtered record list to show all records
        expectedModel.undoFinancialPlanner();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first record deleted again
        expectedModel.redoFinancialPlanner();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> planner book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);

        // single planner book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Record} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted record in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the record object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRecordDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RECORD);
        Model expectedModel = new ModelManager(model.getFinancialPlanner(), new UserPrefs());

        showRecordAtIndex(model, INDEX_SECOND_RECORD);
        Record recordToDelete = model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased());
        expectedModel.deleteRecord(recordToDelete);
        expectedModel.commitFinancialPlanner();

        // delete -> deletes second record in unfiltered record list / first record in filtered record list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts financialplanner back to previous state and filtered record list to show all records
        expectedModel.undoFinancialPlanner();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(recordToDelete, model.getFilteredRecordList().get(INDEX_FIRST_RECORD.getZeroBased()));
        // redo -> deletes same second record in unfiltered record list
        expectedModel.redoFinancialPlanner();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_RECORD);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_RECORD);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_RECORD);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different record -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoRecord(Model model) {
        model.updateFilteredRecordList(p -> false);

        assertTrue(model.getFilteredRecordList().isEmpty());
    }
}
