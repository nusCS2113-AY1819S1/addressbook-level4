package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INSTRUCTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.storage.XmlAdaptedParameter.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalParameters.NAME_PARAMETER;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.testutil.Assert;

public class XmlAdaptedParameterTest {
    private static final String INVALID_NAME = "James's workout&";
    private static final String INVALID_TYPE = "strength + cardio";
    private static final String INVALID_DURATION = "5 minutes";
    private static final String INVALID_DIFFICULTY = "difficult";
    private static final String INVALID_EQUIPMENT = "dumbbell + mat";
    private static final String INVALID_MUSCLE = "bicep + tricep";
    private static final String INVALID_CALORIES = "123 calories";
    //no invalid instructions
    private static final String INVALID_TAG = "#friend";

    @Test
    public void toModelType_validParameterDetails_returnsParameter() throws Exception {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(NAME_PARAMETER);
        assertEquals(NAME_PARAMETER, parameter.toModelType());
    }

    @Test
    public void toModelType_nullPrefix_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(null, "test");
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Prefix");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_NAME.getPrefix(), INVALID_NAME);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_NAME.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_TYPE.getPrefix(), INVALID_TYPE);
        String expectedMessage = Type.MESSAGE_TYPE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_TYPE.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_invalidDuration_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_DURATION.getPrefix(), INVALID_DURATION);
        String expectedMessage = Duration.MESSAGE_DURATION_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_nullDuration_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_DURATION.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_invalidDifficulty_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_DIFFICULTY.getPrefix(), INVALID_DIFFICULTY);
        String expectedMessage = Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_nullDifficulty_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_DIFFICULTY.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_invalidEquipment_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_EQUIPMENT.getPrefix(), INVALID_EQUIPMENT);
        String expectedMessage = Equipment.MESSAGE_EQUIPMENT_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_nullEquipment_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_EQUIPMENT.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_invalidMuscle_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_MUSCLE.getPrefix(), INVALID_MUSCLE);
        String expectedMessage = Muscle.MESSAGE_MUSCLE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_nullMuscle_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_MUSCLE.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_invalidCalories_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_CALORIES.getPrefix(), INVALID_CALORIES);
        String expectedMessage = Calories.MESSAGE_CALORIES_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_nullCalories_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_CALORIES.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    //no invalid instruction

    @Test
    public void toModelType_nullInstruction_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_INSTRUCTION.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_TAG.getPrefix(), INVALID_TAG);
        String expectedMessage = Tag.MESSAGE_TAG_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void toModelType_nullTags_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_TAG.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    //no invalid remark

    @Test
    public void toModelType_nullRemark_throwsIllegalValueException() {
        XmlAdaptedParameter parameter = new XmlAdaptedParameter(PREFIX_REMARK.getPrefix(), null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "value");
        Assert.assertThrows(IllegalValueException.class, expectedMessage, parameter::toModelType);
    }

    @Test
    public void equals() {
        final XmlAdaptedParameter testXml = new XmlAdaptedParameter("name/", "test");

        //check 1: same object -> returns true
        assertEquals(testXml, testXml);

        //check 2: same values -> return true
        XmlAdaptedParameter sameValuesInXml = new XmlAdaptedParameter("name/", "test");
        assertEquals(sameValuesInXml, testXml);

        //check 3: null -> return false
        assertNotEquals(null, testXml);

        //check 4: different XmlAdaptedParameter -> return false
        assertNotEquals(testXml, new XmlAdaptedParameter());

        //check 5: different prefix -> return false
        XmlAdaptedParameter differentPrefix = new XmlAdaptedParameter("muscle/", "test");
        assertNotEquals(testXml, differentPrefix);

        //check 6: different value -> return false
        XmlAdaptedParameter differentValue = new XmlAdaptedParameter("name/", "commando");
        assertNotEquals(testXml, differentValue);
    }
}
