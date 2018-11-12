package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showWorkoutAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_WORKOUT;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackedData;
import seedu.address.model.UserPrefs;
import seedu.address.model.workout.Workout;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(), new TrackedData(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Workout workoutToDelete = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_WORKOUT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_WORKOUT_SUCCESS, workoutToDelete);

        ModelManager expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.deleteWorkout(workoutToDelete);
        expectedModel.commitModel();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);

        Workout workoutToDelete = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_WORKOUT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_WORKOUT_SUCCESS, workoutToDelete);

        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.deleteWorkout(workoutToDelete);
        expectedModel.commitModel();
        showNoWorkout(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);

        Index outOfBoundIndex = INDEX_SECOND_WORKOUT;
        // ensures that outOfBoundIndex is still in bounds of workout book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWorkoutBook().getWorkoutList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Workout workoutToDelete = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_WORKOUT);
        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.deleteWorkout(workoutToDelete);
        expectedModel.commitModel();

        // delete -> first workout deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts workout book back to previous state and filtered workout list to show all workouts
        expectedModel.undoModel();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first workout deleted again
        expectedModel.redoModel();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> workout book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);

        // single workout book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Workout} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted workout in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the workout object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameWorkoutDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_WORKOUT);
        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());

        showWorkoutAtIndex(model, INDEX_SECOND_WORKOUT);
        Workout workoutToDelete = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        expectedModel.deleteWorkout(workoutToDelete);
        expectedModel.commitModel();

        // delete -> deletes second workout in unfiltered workout list / first workout in filtered workout list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts workout book back to previous state and filtered workout list to show all workouts
        expectedModel.undoModel();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(workoutToDelete, model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased()));
        // redo -> deletes same second workout in unfiltered workout list
        expectedModel.redoModel();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_WORKOUT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_WORKOUT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_WORKOUT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different workout -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoWorkout(Model model) {
        model.updateFilteredWorkoutList(p -> false);

        assertTrue(model.getFilteredWorkoutList().isEmpty());
    }
}
