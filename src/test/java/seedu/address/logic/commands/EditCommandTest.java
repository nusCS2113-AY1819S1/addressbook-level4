package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
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
import seedu.address.logic.commands.EditCommand.EditWorkoutDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackedData;
import seedu.address.model.UserPrefs;
import seedu.address.model.WorkoutBook;
import seedu.address.model.workout.Workout;

import seedu.address.testutil.EditWorkoutDescriptorBuilder;
import seedu.address.testutil.WorkoutBuilder;


/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code EditCommand}.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(), new TrackedData(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Workout editedWorkout = new WorkoutBuilder().build();
        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder(editedWorkout).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORKOUT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORKOUT_SUCCESS, editedWorkout);

        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(model.getFilteredWorkoutList().get(0), editedWorkout);
        expectedModel.commitModel();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastWorkout = Index.fromOneBased(model.getFilteredWorkoutList().size());
        Workout lastWorkout = model.getFilteredWorkoutList().get(indexLastWorkout.getZeroBased());

        WorkoutBuilder workoutInList = new WorkoutBuilder(lastWorkout);
        Workout editedWorkout = workoutInList.withName(VALID_NAME_BOB_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT)
                .withDuration(VALID_DURATION_BOB_WORKOUT)
                .withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT).withEquipment(VALID_EQUIPMENT_BOB_WORKOUT)
                .withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                .withCalories(VALID_CALORIES_BOB_WORKOUT).withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT, VALID_TAG_FUTURE).build();

        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withName(VALID_NAME_BOB_WORKOUT)
                .withType(VALID_TYPE_BOB_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT)
                .withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT).withEquipment(VALID_EQUIPMENT_BOB_WORKOUT)
                .withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                .withCalories(VALID_CALORIES_BOB_WORKOUT).withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT, VALID_TAG_FUTURE).build();
        EditCommand editCommand = new EditCommand(indexLastWorkout, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORKOUT_SUCCESS, editedWorkout);

        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(lastWorkout, editedWorkout);
        expectedModel.commitModel();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORKOUT, new EditWorkoutDescriptor());
        Workout editedWorkout = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORKOUT_SUCCESS, editedWorkout);

        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.commitModel();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);

        Workout workoutInFilteredList = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        Workout editedWorkout = new WorkoutBuilder(workoutInFilteredList).withName(VALID_NAME_BOB_WORKOUT).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORKOUT,
                new EditWorkoutDescriptorBuilder().withName(VALID_NAME_BOB_WORKOUT).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_WORKOUT_SUCCESS, editedWorkout);

        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(model.getFilteredWorkoutList().get(0), editedWorkout);
        expectedModel.commitModel();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateWorkoutUnfilteredList_failure() {
        Workout firstWorkout = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder(firstWorkout).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_WORKOUT, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_WORKOUT);
    }

    @Test
    public void execute_duplicateWorkoutFilteredList_failure() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);

        // edit workout in filtered list into a duplicate in workout book
        Workout workoutInList = model.getWorkoutBook().getWorkoutList().get(INDEX_SECOND_WORKOUT.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORKOUT,
                new EditWorkoutDescriptorBuilder(workoutInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_WORKOUT);
    }

    @Test
    public void execute_invalidWorkoutIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withName(VALID_NAME_BOB_WORKOUT).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of workout book
     */
    @Test
    public void execute_invalidWorkoutIndexFilteredList_failure() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);
        Index outOfBoundIndex = INDEX_SECOND_WORKOUT;
        // ensures that outOfBoundIndex is still in bounds of workout book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWorkoutBook().getWorkoutList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditWorkoutDescriptorBuilder().withName(VALID_NAME_BOB_WORKOUT).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Workout editedWorkout = new WorkoutBuilder().build();
        Workout workoutToEdit = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder(editedWorkout).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORKOUT, descriptor);
        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(workoutToEdit, editedWorkout);
        expectedModel.commitModel();

        // edit -> first workout edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts workoutbook back to previous state and filtered workout list to show all workouts
        expectedModel.undoModel();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first workout edited again
        expectedModel.redoModel();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder().withName(VALID_NAME_BOB_WORKOUT).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> workout book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);

        // single workout book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Workout} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited workout in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the workout object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameWorkoutEdited() throws Exception {
        Workout editedWorkout = new WorkoutBuilder().build();
        EditWorkoutDescriptor descriptor = new EditWorkoutDescriptorBuilder(editedWorkout).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_WORKOUT, descriptor);
        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());

        showWorkoutAtIndex(model, INDEX_SECOND_WORKOUT);
        Workout workoutToEdit = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        expectedModel.updateWorkout(workoutToEdit, editedWorkout);
        expectedModel.commitModel();

        // edit -> edits second workout in unfiltered workout list / first workout in filtered workout list
        editCommand.execute(model, commandHistory);

        // undo -> reverts workoutbook back to previous state and filtered workout list to show all workout
        expectedModel.undoModel();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased()), workoutToEdit);
        // redo -> edits same second workout in unfiltered workout list
        expectedModel.redoModel();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_WORKOUT, DESC_AMY_WORKOUT);

        // same values -> returns true
        EditWorkoutDescriptor copyDescriptor = new EditWorkoutDescriptor(DESC_AMY_WORKOUT);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_WORKOUT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_WORKOUT, DESC_AMY_WORKOUT)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_WORKOUT, DESC_BOB_WORKOUT)));
    }

}
