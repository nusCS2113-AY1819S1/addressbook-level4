package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;

import org.junit.Test;

import seedu.address.logic.commands.EditCommand.EditWorkoutDescriptor;
import seedu.address.testutil.EditWorkoutDescriptorBuilder;

public class EditWorkoutDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditWorkoutDescriptor descriptorWithSameValues = new EditWorkoutDescriptor(DESC_AMY_WORKOUT);
        assertTrue(DESC_AMY_WORKOUT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY_WORKOUT.equals(DESC_AMY_WORKOUT));

        // null -> returns false
        assertFalse(DESC_AMY_WORKOUT.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY_WORKOUT.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY_WORKOUT.equals(DESC_BOB_WORKOUT));

        // different name -> returns false
        EditWorkoutDescriptor editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT)
                .withName(VALID_NAME_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));

        // different type -> returns false
        editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));

        // different duration -> returns false
        editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT)
                .build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));

        // different difficulty -> returns false
        editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT)
                .withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));

        // different equipment -> returns false
        editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT)
                .withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));

        // different muscle -> returns false
        editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT)
                .build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));

        // different calories -> returns false
        editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT)
                .build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));

        // different instruction -> returns false
        editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT)
                .withInstruction(VALID_INSTRUCTION_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));

        // different tags -> returns false
        editedAmyWorkout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withTags(VALID_TAG_NIGHT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmyWorkout));
    }
}
