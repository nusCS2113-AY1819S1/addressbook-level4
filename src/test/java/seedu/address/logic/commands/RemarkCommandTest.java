package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB_WORKOUT;
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
import seedu.address.model.WorkoutBook;
import seedu.address.model.workout.Remark;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.RemarkBuilder;
import seedu.address.testutil.WorkoutBuilder;


public class RemarkCommandTest {
    private Model model = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(), new TrackedData(),
            new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Remark remark = new Remark(VALID_REMARK_BOB_WORKOUT);
        Workout remarkedWorkout = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_WORKOUT, remark);
        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_WORKOUT_SUCCESS, remarkedWorkout);
        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(model.getFilteredWorkoutList().get(0), remarkedWorkout);
        expectedModel.commitModel();
        assertCommandSuccess(remarkCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);
        Workout workoutInFilteredList = model.getFilteredWorkoutList().get(INDEX_FIRST_WORKOUT.getZeroBased());
        Workout remarkedWorkout = new WorkoutBuilder(workoutInFilteredList)
                .withRemark(VALID_REMARK_BOB_WORKOUT).build();
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_WORKOUT,
                new RemarkBuilder(new Remark(VALID_REMARK_BOB_WORKOUT)).build());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_WORKOUT_SUCCESS, remarkedWorkout);

        Model expectedModel = new ModelManager(new WorkoutBook(model.getWorkoutBook()), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.updateWorkout(model.getFilteredWorkoutList().get(0), remarkedWorkout);
        expectedModel.commitModel();
        assertCommandSuccess(remarkCommand, model, commandHistory, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidWorkoutIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkoutList().size() + 1);
        Remark remark = new RemarkBuilder(new Remark(VALID_NAME_BOB_WORKOUT)).build();
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, remark);

        assertCommandFailure(remarkCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }
    /**
     * Remark filtered list where index is larger than size of filtered list,
     * but smaller than size of workout book
     */
    @Test
    public void execute_invalidWorkoutIndexFilteredList_failure() {
        showWorkoutAtIndex(model, INDEX_FIRST_WORKOUT);
        Index outOfBoundIndex = INDEX_SECOND_WORKOUT;
        // ensures that outOfBoundIndex is still in bounds of workout book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getWorkoutBook().getWorkoutList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex,
                new RemarkBuilder(new Remark(VALID_NAME_BOB_WORKOUT)).build());

        assertCommandFailure(remarkCommand, model, commandHistory, Messages.MESSAGE_INVALID_WORKOUT_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_WORKOUT,
                new Remark(VALID_REMARK_BOB_WORKOUT));

        // same values -> returns true
        Remark copyRemark = new Remark(VALID_REMARK_BOB_WORKOUT);
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_WORKOUT, copyRemark);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_WORKOUT,
                new Remark(VALID_REMARK_BOB_WORKOUT))));
    }

}
