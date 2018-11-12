package seedu.address.model.workout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BOB_WORKOUT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.WorkoutBuilder;

public class WorkoutTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Workout workout = new WorkoutBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        workout.getTags().remove(0);
    }

    @Test
    public void isSameWorkout() {
        // same object -> returns true
        assertTrue(ALICE_WORKOUT.isSameWorkout(ALICE_WORKOUT));

        // null -> returns false
        assertFalse(ALICE_WORKOUT.isSameWorkout(null));

        // different type and duration -> returns true
        Workout editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT)
                .withDuration(VALID_DURATION_BOB_WORKOUT).build();
        assertTrue(ALICE_WORKOUT.isSameWorkout(editedAliceWorkout));

        // different name only -> returns true
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withName(VALID_NAME_BOB_WORKOUT).build();
        assertTrue(ALICE_WORKOUT.isSameWorkout(editedAliceWorkout));

        // different name and type -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withName(VALID_NAME_BOB_WORKOUT)
                .withType(VALID_TYPE_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.isSameWorkout(editedAliceWorkout));

        // different name and duration -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withName(VALID_NAME_BOB_WORKOUT)
                .withDuration(VALID_DURATION_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.isSameWorkout(editedAliceWorkout));

        // different name and muscle -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withName(VALID_NAME_BOB_WORKOUT)
                .withMuscle(VALID_MUSCLE_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.isSameWorkout(editedAliceWorkout));

        // different attributes -> returns true
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT)
                .withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                .withCalories(VALID_CALORIES_BOB_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        assertTrue(ALICE_WORKOUT.isSameWorkout(editedAliceWorkout));

        // different attributes -> returns true
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT)
                .withDuration(VALID_DURATION_BOB_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                .withCalories(VALID_CALORIES_BOB_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        assertTrue(ALICE_WORKOUT.isSameWorkout(editedAliceWorkout));


        // different attributes -> returns true
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                .withCalories(VALID_CALORIES_BOB_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        assertTrue(ALICE_WORKOUT.isSameWorkout(editedAliceWorkout));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Workout aliceCopy = new WorkoutBuilder(ALICE_WORKOUT).build();
        assertTrue(ALICE_WORKOUT.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE_WORKOUT.equals(ALICE_WORKOUT));

        // null -> returns false
        assertFalse(ALICE_WORKOUT.equals(null));

        // different type of object -> returns false
        assertFalse(ALICE_WORKOUT.equals(5));

        // different workout -> returns false
        assertFalse(ALICE_WORKOUT.equals(BOB_WORKOUT));

        // different name -> returns false
        Workout editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withName(VALID_NAME_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));

        // different type -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));

        // different duration -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));

        // different difficulty -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));

        // different equipment -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));

        // different muscle -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));

        // different calories -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));

        // different instruction -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withInstruction(VALID_INSTRUCTION_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));

        // different tags -> returns false
        editedAliceWorkout = new WorkoutBuilder(ALICE_WORKOUT).withTags(VALID_TAG_NIGHT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAliceWorkout));
    }
}
