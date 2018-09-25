package seedu.address.model.workout;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.address.testutil.WorkoutBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.testutil.TypicalWorkouts.ALICE_WORKOUT;
import static seedu.address.testutil.TypicalWorkouts.BOB_WORKOUT;

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

        // different type and duration -> returns false
        Workout editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.isSameWorkout(editedAlice_Workout));

        // different name -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withName(VALID_NAME_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.isSameWorkout(editedAlice_Workout));

        // same name, same type, different attributes -> returns true
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT).withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        assertTrue(ALICE_WORKOUT.isSameWorkout(editedAlice_Workout));

        // same name, same difficulty, different attributes -> returns true
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        assertTrue(ALICE_WORKOUT.isSameWorkout(editedAlice_Workout));


        // same name, same type, same difficulty, different attributes -> returns true
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_BOB_WORKOUT)
                .withTags(VALID_TAG_NIGHT).build();
        assertTrue(ALICE_WORKOUT.isSameWorkout(editedAlice_Workout));
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

        // different type -> returns false
        assertFalse(ALICE_WORKOUT.equals(5));

        // different workout -> returns false
        assertFalse(ALICE_WORKOUT.equals(BOB_WORKOUT));

        // different name -> returns false
        Workout editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withName(VALID_NAME_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));

        // different type -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));

        // different duration -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));

        // different difficulty -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));

        // different equipment -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));

        // different muscle -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));

        // different calories -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));

        // different instruction -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withType(VALID_INSTRUCTION_BOB_WORKOUT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));

        // different tags -> returns false
        editedAlice_Workout = new WorkoutBuilder(ALICE_WORKOUT).withTags(VALID_TAG_NIGHT).build();
        assertFalse(ALICE_WORKOUT.equals(editedAlice_Workout));
    }
}
