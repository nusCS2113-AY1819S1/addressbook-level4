package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalParameters.getTypicalTrackedDataList;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RecommendArguments;
import seedu.address.model.TrackedData;
import seedu.address.model.UserPrefs;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Mode;
import seedu.address.model.workout.Workout;
import seedu.address.model.workout.WorkoutsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code RecommendCommand}.
 */
public class RecommendCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalWorkoutBook(), getTypicalTrackedDataList(),
                new TrackedData(), new UserPrefs());
    }

    @Test
    public void execute_newRecommendArguments_success() {

        // Single Recommend Calories
        Optional<Calories> calories = Optional.of(new Calories("20"));
        Optional<Mode> mode = Optional.of(new Mode("single"));
        RecommendArguments validRecommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(false)).withMode(mode).build();

        Model expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());

        List<Workout> filteredInternalList = expectedModel.getFinalFilteredInternalList(validRecommendArguments);
        WorkoutsPredicate workoutsPredicate = new WorkoutsPredicate(filteredInternalList);
        expectedModel.updateFilteredWorkoutList(workoutsPredicate);

        assertCommandSuccess(new RecommendCommand(validRecommendArguments), model, commandHistory,
                RecommendCommand.MESSAGE_SUCCESS, expectedModel);

        // Multiple Recommend Difficulty, Duration
        Optional<Difficulty> difficulty = Optional.of(new Difficulty("intermediate"));
        Optional<Duration> duration = Optional.of(new Duration("30m"));
        mode = Optional.of(new Mode("multiple 2"));
        validRecommendArguments = new RecommendArguments.Builder().withDuration(duration, Optional.of(false))
                .withDifficulty(difficulty, Optional.of(false))
                .withMode(mode).build();

        expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());

        filteredInternalList = expectedModel.getFinalFilteredInternalList(validRecommendArguments);
        int multipleInteger = validRecommendArguments.getMode()
                .getMultipleInteger(validRecommendArguments.getMode().toString());
        List<Workout> subFilteredInternalList = filteredInternalList.subList(0, multipleInteger);
        workoutsPredicate = new WorkoutsPredicate(subFilteredInternalList);
        expectedModel.updateFilteredWorkoutList(workoutsPredicate);

        assertCommandSuccess(new RecommendCommand(validRecommendArguments), model, commandHistory,
                RecommendCommand.MESSAGE_SUCCESS, expectedModel);

        // All Recommend Calories, Difficulty and Duration
        calories = Optional.of(new Calories("100"));
        difficulty = Optional.of(new Difficulty("beginner"));
        duration = Optional.of(new Duration("20m"));
        mode = Optional.of(new Mode("all"));
        validRecommendArguments = new RecommendArguments.Builder().withCalories(calories, Optional.of(false))
                .withDifficulty(difficulty, Optional.of(false))
                .withDuration(duration, Optional.of(false))
                .withMode(mode).build();

        expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());

        filteredInternalList = expectedModel.getFinalFilteredInternalList(validRecommendArguments);
        workoutsPredicate = new WorkoutsPredicate(filteredInternalList);
        expectedModel.updateFilteredWorkoutList(workoutsPredicate);

        assertCommandSuccess(new RecommendCommand(validRecommendArguments), model, commandHistory,
                RecommendCommand.MESSAGE_SUCCESS, expectedModel);

        // All Recommend 3 Optionals
        calories = Optional.of(new Calories("500"));
        difficulty = Optional.of(new Difficulty("beginner"));
        duration = Optional.of(new Duration("1000m"));
        mode = Optional.of(new Mode("single"));
        validRecommendArguments = new RecommendArguments.Builder().withCalories(calories, Optional.of(true))
                .withDifficulty(difficulty, Optional.of(true))
                .withDuration(duration, Optional.of(true))
                .withMode(mode).build();

        expectedModel = new ModelManager(model.getWorkoutBook(), model.getTrackedDataList(),
                model.getTrackedData(), new UserPrefs());

        filteredInternalList = expectedModel.getFinalFilteredInternalList(validRecommendArguments);
        workoutsPredicate = new WorkoutsPredicate(filteredInternalList);
        expectedModel.updateFilteredWorkoutList(workoutsPredicate);

        assertCommandSuccess(new RecommendCommand(validRecommendArguments), model, commandHistory,
                RecommendCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_workoutNotFound_throwsCommandException() {
        Optional<Difficulty> difficulty = Optional.of(new Difficulty("advanced"));
        Optional<Duration> duration = Optional.of(new Duration("1m"));
        Optional<Mode> mode = Optional.of(new Mode("single"));
        RecommendArguments noMatchRecommendArguments = new RecommendArguments.Builder()
                .withDifficulty(difficulty, Optional.of(false))
                .withDuration(duration, Optional.of(false))
                .withMode(mode).build();

        assertCommandFailure(new RecommendCommand(noMatchRecommendArguments), model, commandHistory,
                RecommendCommand.MESSAGE_NO_SUCH_WORKOUT);
    }

    @Test
    public void execute_invalidSize_throwsCommandException() {
        Optional<Difficulty> difficulty = Optional.of(new Difficulty("advanced"));
        Optional<Mode> mode = Optional.of(new Mode("multiple 3"));
        RecommendArguments invalidSizeRecommendArguments = new RecommendArguments.Builder().withDifficulty(difficulty,
                Optional.of(false)).withMode(mode).build();
        assertCommandFailure(new RecommendCommand(invalidSizeRecommendArguments), model, commandHistory,
                String.format(RecommendCommand.MESSAGE_INVALID_WORKOUTS_RECOMMENDED_SIZE, 2));
    }

}
