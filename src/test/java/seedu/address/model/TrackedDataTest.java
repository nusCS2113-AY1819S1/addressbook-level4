package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.getTypicalTrackedData;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.workout.Workout;
import seedu.address.testutil.WorkoutBuilder;

public class TrackedDataTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TrackedData trackedData = new TrackedData();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), trackedData.getTrackedData());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        trackedData.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTrackedData_replacesData() {
        TrackedData newData = getTypicalTrackedData();
        trackedData.resetData(newData);
        assertEquals(newData, trackedData);
    }

    @Test
    public void hasWorkout_nullWorkout_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        trackedData.hasWorkout(null);
    }

    @Test
    public void hasWorkout_workoutNotInTrackedData_returnsFalse() {
        assertFalse(trackedData.hasWorkout(ALICE_WORKOUT));
    }

    @Test
    public void hasWorkout_workoutInTrackedData_returnsTrue() {
        trackedData.addWorkout(ALICE_WORKOUT);
        assertTrue(trackedData.hasWorkout(ALICE_WORKOUT));
    }

    @Test
    public void hasWorkout_workoutWithSameIdentityFieldsInTrackedData_returnsTrue() {
        trackedData.addWorkout(ALICE_WORKOUT);
        Workout editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        assertTrue(trackedData.hasWorkout(editedAliceWorkout));
    }

    @Test
    public void getTrackedData_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        trackedData.getTrackedData().remove(0);
    }

    /**
     * A stub ReadOnlyTrackedData whose workouts list can violate interface constraints.
     */
    private static class TrackedDataStub implements ReadOnlyTrackedData {
        private final ObservableList<Workout> workouts = FXCollections.observableArrayList();

        TrackedDataStub(Collection<Workout> workouts) {
            this.workouts.setAll(workouts);
        }

        @Override
        public ObservableList<Workout> getTrackedData() {
            return workouts;
        }
    }

}
