package seedu.address.storage;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EQUIPMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MUSCLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Parameter;
import seedu.address.model.workout.Type;

/**
 * JAXB-friendly version of the Workout.
 */
public class XmlAdaptedParameter {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Parameter's %s field is missing!";

    @XmlElement(required = true)
    private String prefix;
    @XmlElement(required = true)
    private String value;

    /**
     * Constructs an XmlAdaptedParameter.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedParameter() {}

    /**
     * Constructs an {@code XmlAdaptedParameter} with the given parameter details.
     */
    public XmlAdaptedParameter(String prefix, String value) {
        this.prefix = prefix;
        this.value = value;
    }

    /**
     * Converts a given Parameter into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedParameter
     */
    public XmlAdaptedParameter(Parameter source) {
        prefix = source.getPrefix().getPrefix();
        value = source.getValue();
    }

    /**
     * Converts this jaxb-friendly adapted parameter object into the model's Parameter object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Workout
     */
    public Parameter toModelType() throws IllegalValueException {
        if (prefix == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Prefix.class.getSimpleName()));
        }
        final Prefix modelPrefix = new Prefix(prefix);

        if (value == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "value"));
        }

        // no invalid instruction or remark
        if (modelPrefix.equals(PREFIX_NAME)) {
            if (!Name.isValidName(value)) {
                throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
            }
        } else if (modelPrefix.equals(PREFIX_TYPE)) {
            if (!Type.isValidType(value)) {
                throw new IllegalValueException(Type.MESSAGE_TYPE_CONSTRAINTS);
            }
        } else if (modelPrefix.equals(PREFIX_DURATION)) {
            if (!Duration.isValidDuration(value)) {
                throw new IllegalValueException(Duration.MESSAGE_DURATION_CONSTRAINTS);
            }
        } else if (modelPrefix.equals(PREFIX_DIFFICULTY)) {
            if (!Difficulty.isValidDifficulty(value)) {
                throw new IllegalValueException(Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);
            }
        } else if (modelPrefix.equals(PREFIX_EQUIPMENT)) {
            if (!Equipment.isValidEquipment(value)) {
                throw new IllegalValueException(Equipment.MESSAGE_EQUIPMENT_CONSTRAINTS);
            }
        } else if (modelPrefix.equals(PREFIX_MUSCLE)) {
            if (!Muscle.isValidMuscle(value)) {
                throw new IllegalValueException(Muscle.MESSAGE_MUSCLE_CONSTRAINTS);
            }
        } else if (modelPrefix.equals(PREFIX_CALORIES)) {
            if (!Calories.isValidCalories(value)) {
                throw new IllegalValueException(Calories.MESSAGE_CALORIES_CONSTRAINTS);
            }
        } else if (modelPrefix.equals(PREFIX_TAG)) {
            if (!Tag.isValidTagName(value)) {
                throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
            }
        }
        final String modelValue = value;

        return new Parameter(modelPrefix, modelValue);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedParameter)) {
            return false;
        }

        XmlAdaptedParameter otherParameter = (XmlAdaptedParameter) other;
        return Objects.equals(prefix, otherParameter.prefix)
                && Objects.equals(value, otherParameter.value);
    }
}
