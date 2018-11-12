package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTrackedData;
import seedu.address.model.ReadOnlyTrackedDataList;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.RecommendArguments;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Mode;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.WorkoutBuilder;

public class RecommendCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private ModelStub modelStub = new ModelStub();

    @Before
    public void setUpModelStub() {
        Workout validWorkout = new WorkoutBuilder().build();
        modelStub.addWorkout(validWorkout);
    }

    @Test
    public void constructor_nullRecommendArguments_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RecommendCommand(null);
    }

    @Test
    public void execute_acceptedByModel_recommendSuccessful() throws Exception {
        // Valid Single Recommend Calories
        Optional<Calories> calories = Optional.of(new Calories("1"));
        Optional<Mode> mode = Optional.of(new Mode("single"));
        RecommendArguments recommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(false)).withMode(mode).build();
        CommandResult commandResult = new RecommendCommand(recommendArguments).execute(modelStub, commandHistory);

        assertEquals(RecommendCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Valid Recommend Multiple Difficulty, Duration
        Optional<Difficulty> difficulty = Optional.of(new Difficulty("beginner"));
        Optional<Duration> duration = Optional.of(new Duration("1m"));
        mode = Optional.of(new Mode("multiple 1"));
        recommendArguments = new RecommendArguments.Builder().withDifficulty(difficulty, Optional.of(false))
                .withDuration(duration, Optional.of(false))
                .withMode(mode).build();
        commandResult = new RecommendCommand(recommendArguments).execute(modelStub, commandHistory);

        assertEquals(RecommendCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Valid Recommend All Calories, Difficulty, Duration
        duration = Optional.of(new Duration("1000m"));
        difficulty = Optional.of(new Difficulty("intermediate"));
        calories = Optional.of(new Calories("1000"));
        mode = Optional.of(new Mode("all"));
        recommendArguments = new RecommendArguments.Builder().withDuration(duration, Optional.of(false))
                .withCalories(calories, Optional.of(false))
                .withDifficulty(difficulty, Optional.of(false))
                .withMode(mode).build();
        commandResult = new RecommendCommand(recommendArguments).execute(modelStub, commandHistory);

        assertEquals(RecommendCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Valid Recommend Single 1 Optionals
        calories = Optional.of(new Calories("500"));
        difficulty = Optional.of(new Difficulty("advanced"));
        duration = Optional.of(new Duration("500m"));
        mode = Optional.of(new Mode("single"));
        recommendArguments = new RecommendArguments.Builder().withCalories(calories, Optional.of(true))
                .withDifficulty(difficulty, Optional.of(false))
                .withDuration(duration, Optional.of(false))
                .withMode(mode).build();
        commandResult = new RecommendCommand(recommendArguments).execute(modelStub, commandHistory);

        assertEquals(RecommendCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Valid Recommend Multiple 2 Optionals
        calories = Optional.of(new Calories("250"));
        difficulty = Optional.of(new Difficulty("beginner"));
        duration = Optional.of(new Duration("250m"));
        mode = Optional.of(new Mode("multiple 1"));
        recommendArguments = new RecommendArguments.Builder().withCalories(calories, Optional.of(true))
                .withDifficulty(difficulty, Optional.of(true))
                .withDuration(duration, Optional.of(false))
                .withMode(mode).build();
        commandResult = new RecommendCommand(recommendArguments).execute(modelStub, commandHistory);

        assertEquals(RecommendCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Valid Recommend All 3 Optionals
        calories = Optional.of(new Calories("750"));
        difficulty = Optional.of(new Difficulty("intermediate"));
        duration = Optional.of(new Duration("750m"));
        mode = Optional.of(new Mode("all"));
        recommendArguments = new RecommendArguments.Builder().withCalories(calories, Optional.of(true))
                .withDifficulty(difficulty, Optional.of(true))
                .withDuration(duration, Optional.of(true))
                .withMode(mode).build();
        commandResult = new RecommendCommand(recommendArguments).execute(modelStub, commandHistory);

        assertEquals(RecommendCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_workoutNotFound_throwsCommandException() throws Exception {
        // Valid Single Recommend Calories
        Optional<Calories> calories = Optional.of(new Calories("999"));
        Optional<Mode> mode = Optional.of(new Mode("single"));
        RecommendArguments recommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(false)).withMode(mode).build();
        RecommendCommand recommendCommand = new RecommendCommand(recommendArguments);

        thrown.expect(CommandException.class);
        thrown.expectMessage(recommendCommand.MESSAGE_NO_SUCH_WORKOUT);
        recommendCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_invalidSize_throwsCommandException() throws Exception {
        // Valid Recommend Multiple Calories
        Optional<Calories> calories = Optional.of(new Calories("250"));
        Optional<Mode> mode = Optional.of(new Mode("multiple 2"));
        RecommendArguments recommendArguments = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(false)).withMode(mode).build();
        RecommendCommand recommendCommand = new RecommendCommand(recommendArguments);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(recommendCommand.MESSAGE_INVALID_WORKOUTS_RECOMMENDED_SIZE, "1"));
        recommendCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        // Valid Single Recommend Duration
        Optional<Duration> duration = Optional.of(new Duration("1m"));
        Optional<Mode> mode = Optional.of(new Mode("single"));
        RecommendArguments recommendArgumentsDuration = new RecommendArguments.Builder().withDuration(duration,
                Optional.of(false)).withMode(mode).build();

        // Valid Single Recommend Optional Calories, Difficulty and Duration
        Optional<Calories> calories = Optional.of(new Calories("1000"));
        Optional<Difficulty> difficulty = Optional.of(new Difficulty("intermediate"));
        duration = Optional.of(new Duration("1000m"));
        mode = Optional.of(new Mode("single"));
        RecommendArguments recommendArgumentsCalories = new RecommendArguments.Builder().withCalories(calories,
                Optional.of(true)).withDifficulty(difficulty, Optional.of(true)).withDuration(duration,
                Optional.of(true)).withMode(mode).build();
        RecommendCommand recommendCommandArgumentsDuration = new RecommendCommand(recommendArgumentsDuration);
        RecommendCommand recommendCommandArgumentsCalories = new RecommendCommand(recommendArgumentsCalories);

        // Same object -> returns true
        assertTrue(recommendCommandArgumentsDuration.equals(recommendCommandArgumentsDuration));

        // Same values -> return true
        RecommendCommand recommendArgumentsDurationCopy = new RecommendCommand(recommendArgumentsDuration);
        assertTrue(recommendCommandArgumentsDuration.equals(recommendArgumentsDurationCopy));

        // Different types -> return false
        assertFalse(recommendCommandArgumentsDuration.equals(1));

        // Null -> returns false
        assertFalse(recommendCommandArgumentsDuration.equals(null));

        // Different recommendArguments -> returns false
        assertFalse(recommendCommandArgumentsDuration.equals(recommendCommandArgumentsCalories));
    }

    /**
     * A model stub.
     */
    private class ModelStub implements Model {
        final ArrayList<Workout> workoutsAdded = new ArrayList<>();

        @Override
        public void addWorkout(Workout workout) {
            requireNonNull(workout);
            workoutsAdded.add(workout);
        }

        @Override
        public void resetData(ReadOnlyWorkoutBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyWorkoutBook getWorkoutBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTrackedDataList getTrackedDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyTrackedData getTrackedData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasWorkout(Workout workout) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteWorkout(Workout target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateWorkout(Workout target, Workout editedWorkout) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Workout> getFilteredWorkoutList() {
            return FXCollections.observableArrayList(workoutsAdded);
        }

        @Override
        public List<Workout> getFinalFilteredInternalList(RecommendArguments recommendArguments) {
            if (!recommendArguments.isCaloriesNull() && recommendArguments.getCalories().toString() == "999") {
                return Collections.emptyList();
            } else {
                return getFilteredWorkoutList();
            }
        }

        @Override
        public void updateFilteredWorkoutList(Predicate<Workout> predicate) {
        }

        @Override
        public ObservableList<Parameter> getFilteredTrackedDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTrackedDataList(Predicate<Parameter> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Workout> getFilteredTrackedData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTrackedData(Predicate<Workout> predicate) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public boolean canUndoModel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoModel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoModel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoModel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoWorkoutBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoWorkoutBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoWorkoutBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoWorkoutBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTrackedDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTrackedDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTrackedDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTrackedDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoTrackedData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoTrackedData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoTrackedData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoTrackedData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitModel() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitWorkoutBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTrackedDataList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitTrackedData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredWorkoutList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addDataToTrack(Parameter parameter) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeDataFromTrack(Parameter parameter) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void checkDataForTrack(Workout workout) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasParameter(Parameter parameter) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
