package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EQUIPMENT_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MUSCLE_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CALORIES_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INSTRUCTION_BOB_WORKOUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NIGHT;

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
        EditWorkoutDescriptor editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withName(VALID_NAME_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));

        // different type -> returns false
        editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withType(VALID_TYPE_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));

        // different duration -> returns false
        editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withDuration(VALID_DURATION_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));

        // different difficulty -> returns false
        editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withDifficulty(VALID_DIFFICULTY_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));

        // different equipment -> returns false
        editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withEquipment(VALID_EQUIPMENT_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));

        // different muscle -> returns false
        editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withMuscle(VALID_MUSCLE_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));

        // different calories -> returns false
        editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withCalories(VALID_CALORIES_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));

        // different instruction -> returns false
        editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withInstruction(VALID_INSTRUCTION_BOB_WORKOUT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));

        // different tags -> returns false
        editedAmy_Workout = new EditWorkoutDescriptorBuilder(DESC_AMY_WORKOUT).withTags(VALID_TAG_NIGHT).build();
        assertFalse(DESC_AMY_WORKOUT.equals(editedAmy_Workout));
    }
}