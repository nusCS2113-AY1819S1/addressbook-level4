package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TrackedData;
import seedu.address.model.UserPrefs;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.WorkoutBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(),
                new TrackedData(), new UserPrefs());
    }

    @Test
    public void execute_newWorkout_success() {
        Workout validWorkout = new WorkoutBuilder().build();

        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());
        expectedModel.addWorkout(validWorkout);
        expectedModel.commitModel();

        assertCommandSuccess(new AddCommand(validWorkout), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validWorkout), expectedModel);
    }

    @Test
    public void execute_duplicateWorkout_throwsCommandException() {
        Workout workoutInList = model.getWorkoutBook().getWorkoutList().get(0);
        assertCommandFailure(new AddCommand(workoutInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_WORKOUT);
    }

}
