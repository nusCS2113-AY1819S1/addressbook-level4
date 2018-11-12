package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.XmlAdaptedWorkout.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalWorkouts.BENSON_WORKOUT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;

import seedu.address.testutil.Assert;

public class XmlAdaptedWorkoutTest {
    private static final String INVALID_NAME = "James's workout&";
    private static final String INVALID_TYPE = "strength + cardio";
    private static final String INVALID_DURATION = "5 minutes";
    private static final String INVALID_DIFFICULTY = "difficult";
    private static final String INVALID_EQUIPMENT = "dumbbell + mat";
    private static final String INVALID_MUSCLE = "bicep + tricep";
    private static final String INVALID_CALORIES = "123 calories";
    //no invalid instructions
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON_WORKOUT.getName().toString();
    private static final String VALID_TYPE = BENSON_WORKOUT.getType().toString();
    private static final String VALID_DURATION = BENSON_WORKOUT.getDuration().toString();
    private static final String VALID_DIFFICULTY = BENSON_WORKOUT.getDifficulty().toString();
    private static final String VALID_EQUIPMENT = BENSON_WORKOUT.getEquipment().toString();
    private static final String VALID_MUSCLE = BENSON_WORKOUT.getMuscle().toString();
    private static final String VALID_CALORIES = BENSON_WORKOUT.getCalories().toString();
    private static final String VALID_INSTRUCTION = BENSON_WORKOUT.getInstruction().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = BENSON_WORKOUT.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validWorkoutDetails_returnsWorkout() throws Exception {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(BENSON_WORKOUT);
        assertEquals(BENSON_WORKOUT, workout.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedWorkout workout =
                new XmlAdaptedWorkout(INVALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY, VALID_EQUIPMENT,
                        VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(null, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, INVALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = Type.MESSAGE_TYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, null, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, INVALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = Duration.MESSAGE_DURATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, null, VALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Duration.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_invalidDifficulty_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, INVALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullDifficulty_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, null,
                VALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Difficulty.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_invalidEquipment_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                INVALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = Equipment.MESSAGE_EQUIPMENT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullEquipment_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                null, VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Equipment.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_invalidMuscle_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, INVALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = Muscle.MESSAGE_MUSCLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullMuscle_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, null, VALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Muscle.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_invalidCalories_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, INVALID_CALORIES, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = Calories.MESSAGE_CALORIES_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_nullCalories_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, null, VALID_INSTRUCTION, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Calories.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    //no invalid instruction

    @Test
    public void toModelType_nullInstruction_throwsIllegalValueException() {
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT, VALID_MUSCLE, VALID_CALORIES, null, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Instruction.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, workout::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedWorkout workout = new XmlAdaptedWorkout(VALID_NAME, VALID_TYPE, VALID_DURATION, VALID_DIFFICULTY,
                VALID_EQUIPMENT,
                VALID_MUSCLE, VALID_CALORIES, VALID_INSTRUCTION, invalidTags, null);
        Assert.assertThrows(IllegalValueException.class, workout::toModelType);
    }

}
