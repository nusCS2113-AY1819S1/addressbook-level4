package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyTrackedData;
import seedu.address.model.ReadOnlyTrackedDataList;
import seedu.address.model.ReadOnlyWorkoutBook;
import seedu.address.model.RecommendArguments;
import seedu.address.model.WorkoutBook;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.WorkoutBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_workoutAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingWorkoutAdded modelStub = new ModelStubAcceptingWorkoutAdded();
        Workout validWorkout = new WorkoutBuilder().build();

        CommandResult commandResult = new AddCommand(validWorkout).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validWorkout), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validWorkout), modelStub.workoutsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateWorkout_throwsCommandException() throws Exception {
        Workout validWorkout = new WorkoutBuilder().build();
        AddCommand addCommand = new AddCommand(validWorkout);
        ModelStub modelStub = new ModelStubWithWorkout(validWorkout);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_WORKOUT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Workout aliceWorkout = new WorkoutBuilder().withName("Alice's workout").build();
        Workout bobWorkout = new WorkoutBuilder().withName("Bob's workout").build();
        AddCommand addAliceWorkoutCommand = new AddCommand(aliceWorkout);
        AddCommand addBobWorkoutCommand = new AddCommand(bobWorkout);

        // same object -> returns true
        assertTrue(addAliceWorkoutCommand.equals(addAliceWorkoutCommand));

        // same values -> returns true
        AddCommand addAliceWorkoutCommandCopy = new AddCommand(aliceWorkout);
        assertTrue(addAliceWorkoutCommand.equals(addAliceWorkoutCommandCopy));

        // different types -> returns false
        assertFalse(addAliceWorkoutCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceWorkoutCommand.equals(null));

        // different workout -> returns false
        assertFalse(addAliceWorkoutCommand.equals(addBobWorkoutCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addWorkout(Workout workout) {
            throw new AssertionError("This method should not be called.");
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Workout> getFinalFilteredInternalList(RecommendArguments recommendArguments) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredWorkoutList(Predicate<Workout> predicate) {
            throw new AssertionError("This method should not be called.");
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

    /**
     * A Model stub that contains a single workout.
     */
    private class ModelStubWithWorkout extends ModelStub {
        private final Workout workout;

        ModelStubWithWorkout(Workout workout) {
            requireNonNull(workout);
            this.workout = workout;
        }

        @Override
        public boolean hasWorkout(Workout workout) {
            requireNonNull(workout);
            return this.workout.isSameWorkout(workout);
        }
    }

    /**
     * A Model stub that always accept the workout being added.
     */
    private class ModelStubAcceptingWorkoutAdded extends ModelStub {
        final ArrayList<Workout> workoutsAdded = new ArrayList<>();

        @Override
        public boolean hasWorkout(Workout workout) {
            requireNonNull(workout);
            return workoutsAdded.stream().anyMatch(workout::isSameWorkout);
        }

        @Override
        public void addWorkout(Workout workout) {
            requireNonNull(workout);
            workoutsAdded.add(workout);
        }

        @Override
        public void commitModel() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyWorkoutBook getWorkoutBook() {
            return new WorkoutBook();
        }
    }

}
