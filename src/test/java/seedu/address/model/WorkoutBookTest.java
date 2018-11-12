package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.getTypicalWorkoutBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.workout.Workout;
import seedu.address.model.workout.exceptions.DuplicateWorkoutException;
import seedu.address.testutil.WorkoutBuilder;

public class WorkoutBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final WorkoutBook workoutBook = new WorkoutBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), workoutBook.getWorkoutList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        workoutBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyWorkoutBook_replacesData() {
        WorkoutBook newData = getTypicalWorkoutBook();
        workoutBook.resetData(newData);
        assertEquals(newData, workoutBook);
    }

    @Test
    public void resetData_withDuplicateWorkouts_throwsDuplicateWorkoutException() {
        // Two workouts with the same identity fields
        Workout editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        List<Workout> newWorkouts = Arrays.asList(ALICE_WORKOUT, editedAliceWorkout);
        WorkoutBookStub newData = new WorkoutBookStub(newWorkouts);

        thrown.expect(DuplicateWorkoutException.class);
        workoutBook.resetData(newData);
    }

    @Test
    public void hasWorkout_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        workoutBook.hasWorkout(null);
    }

    @Test
    public void hasWorkout_workoutNotInWorkoutBook_returnsFalse() {
        assertFalse(workoutBook.hasWorkout(ALICE_WORKOUT));
    }

    @Test
    public void hasWorkout_workoutInWorkoutBook_returnsTrue() {
        workoutBook.addWorkout(ALICE_WORKOUT);
        assertTrue(workoutBook.hasWorkout(ALICE_WORKOUT));
    }

    @Test
    public void hasWorkout_workoutWithSameIdentityFieldsInWorkoutBook_returnsTrue() {
        workoutBook.addWorkout(ALICE_WORKOUT);
        Workout editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        assertTrue(workoutBook.hasWorkout(editedAliceWorkout));
    }

    @Test
    public void getWorkoutList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        workoutBook.getWorkoutList().remove(0);
    }

    /**
     * A stub ReadOnlyWorkoutBook whose workouts list can violate interface constraints.
     */
    private static class WorkoutBookStub implements ReadOnlyWorkoutBook {
        private final ObservableList<Workout> workouts = FXCollections.observableArrayList();

        WorkoutBookStub(Collection<Workout> workouts) {
            this.workouts.setAll(workouts);
        }

        @Override
        public ObservableList<Workout> getWorkoutList() {
            return workouts;
        }
    }

}
