package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.CALORIES_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.CALORIES_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DIFFICULTY_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.EQUIPMENT_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.EQUIPMENT_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.INSTRUCTION_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CALORIES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DIFFICULTY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EQUIPMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MUSCLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
//import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_CURRENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.MUSCLE_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FUTURE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WORKOUTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BOB_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
//import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.WorkoutBuilder;
import seedu.address.testutil.WorkoutUtil;

public class EditCommandSystemTest extends WorkoutBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ------------------ */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_WORKOUT;
        String command = "  " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB_WORKOUT
                + "  " + TYPE_DESC_BOB_WORKOUT + "  " + DURATION_DESC_BOB_WORKOUT + "  " + DIFFICULTY_DESC_BOB_WORKOUT
                + "  " + EQUIPMENT_DESC_BOB_WORKOUT + "  " + MUSCLE_DESC_BOB_WORKOUT + "  " + CALORIES_DESC_BOB_WORKOUT
                + "  " + INSTRUCTION_DESC_BOB_WORKOUT + "  " + TAG_DESC_NIGHT + "  ";
        Workout editedWorkout = new WorkoutBuilder(BOB_WORKOUT).withTags(VALID_TAG_NIGHT, VALID_TAG_FUTURE).build();
        assertCommandSuccess(command, index, editedWorkout);

        /* Case: undo editing the last workout in the list -> last workout restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last workout in the list -> last workout edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateWorkout(
                getModel().getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased()), editedWorkout);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a workout with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandSuccess(command, index, BOB_WORKOUT);

        /* Case: clear tags -> cleared except state tag */
        index = INDEX_FIRST_WORKOUT;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Workout workoutToEdit = getModel().getFilteredWorkoutList().get(index.getZeroBased());
        editedWorkout = new WorkoutBuilder(workoutToEdit).withTags(VALID_TAG_FUTURE).build();
        assertCommandSuccess(command, index, editedWorkout);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------- */

        /**
         * this test is causing an "Exception in Async Thread", can't find a way to solve it at the moment
         */
        /* Case: filtered workout list, edit index within bounds of address book and workout list -> edited */

        showWorkoutsWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_WORKOUT;
        assertTrue(index.getZeroBased() < getModel().getFilteredWorkoutList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + TYPE_DESC_BOB_WORKOUT;
        workoutToEdit = getModel().getFilteredWorkoutList().get(index.getZeroBased());
        editedWorkout = new WorkoutBuilder(workoutToEdit).withType(VALID_TYPE_BOB_WORKOUT).build();
        assertCommandSuccess(command, index, editedWorkout);

        /* Case: filtered workout list, edit index within bounds of address book but out of bounds of workout list
         * -> rejected
         */
        showWorkoutsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getWorkoutBook().getWorkoutList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB_WORKOUT,
                Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a workout card is selected -------------------- */

        /* Case: selects first card in the workout list, edit a workout -> edited, card selection remains unchanged
        but browser url changes
         /*
        showAllWorkouts();
        index = INDEX_FIRST_WORKOUT;
        selectWorkout(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY_WORKOUT + TYPE_DESC_AMY_WORKOUT
                + DURATION_DESC_AMY_WORKOUT + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT
                + MUSCLE_DESC_AMY_WORKOUT + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_AMY_WORKOUT + TAG_DESC_MORNING;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new workout's name
        assertCommandSuccess(command, index, AMY_WORKOUT, index);

        /* --------------------------------- Performing invalid edit operation --------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB_WORKOUT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB_WORKOUT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredWorkoutList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB_WORKOUT,
                Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB_WORKOUT,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                        + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid type -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                        + INVALID_TYPE_DESC, Type.MESSAGE_TYPE_CONSTRAINTS);

        /* Case: invalid duration -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                        + INVALID_DURATION_DESC, Duration.MESSAGE_DURATION_CONSTRAINTS);

        /* Case: invalid difficulty -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                        + INVALID_DIFFICULTY_DESC, Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);

        /* Case: invalid equipment -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                        + INVALID_EQUIPMENT_DESC, Equipment.MESSAGE_EQUIPMENT_CONSTRAINTS);

        /* Case: invalid muscle -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                        + INVALID_MUSCLE_DESC, Muscle.MESSAGE_MUSCLE_CONSTRAINTS);

        /* Case: invalid calories -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                        + INVALID_CALORIES_DESC, Calories.MESSAGE_CALORIES_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: invalid tag(current) -> rejected
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_WORKOUT.getOneBased()
                + INVALID_TAG_CURRENT, AddCommand.MESSAGE_TAG_FAILURE); */

        /* Case: edit a workout with new values same as another workout's values -> rejected */
        executeCommand(WorkoutUtil.getAddCommand(BOB_WORKOUT));
        index = INDEX_FIRST_WORKOUT;
        assertFalse(getModel().getFilteredWorkoutList().get(index.getZeroBased()).equals(BOB_WORKOUT));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);

        /* Case: edit a workout with new values same as another workout's values but with different tags
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);

        /* Case: edit a workout with new values same as another workout's values but with different type
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_AMY_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);

        /* Case: edit a workout with new values same as another workout's values but with different duration
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_AMY_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);

        /* Case: edit a workout with new values same as another workout's values but with different difficulty
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);

        /* Case: edit a workout with new values same as another workout's values but with different equipment
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_BOB_WORKOUT + EQUIPMENT_DESC_AMY_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);

        /* Case: edit a workout with new values same as another workout's values but with different muscle
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_AMY_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);

        /* Case: edit a workout with new values same as another workout's values but with different calories
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_AMY_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);

        /* Case: edit a workout with new values same as another workout's values but with different instruction
         * -> rejected
         */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB_WORKOUT + TYPE_DESC_BOB_WORKOUT
                + DURATION_DESC_BOB_WORKOUT + DIFFICULTY_DESC_AMY_WORKOUT + EQUIPMENT_DESC_BOB_WORKOUT
                + MUSCLE_DESC_BOB_WORKOUT + CALORIES_DESC_BOB_WORKOUT + INSTRUCTION_DESC_BOB_WORKOUT + TAG_DESC_NIGHT;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_WORKOUT);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Workout, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Workout, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Workout editedWorkout) {
        assertCommandSuccess(command, toEdit, editedWorkout, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,
     * <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the workout at index {@code toEdit} being
     * updated to values specified {@code editedWorkout}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Workout editedWorkout,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateWorkout(expectedModel.getFilteredWorkoutList().get(toEdit.getZeroBased()), editedWorkout);
        expectedModel.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_WORKOUT_SUCCESS, editedWorkout), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see WorkoutBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see WorkoutBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredWorkoutList(PREDICATE_SHOW_ALL_WORKOUTS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code WorkoutBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see WorkoutBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
