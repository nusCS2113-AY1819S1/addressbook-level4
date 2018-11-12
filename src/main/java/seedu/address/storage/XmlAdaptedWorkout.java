package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Remark;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Workout;

/**
 * JAXB-friendly version of the Workout
 */
public class XmlAdaptedWorkout {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Workout's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String type;
    @XmlElement(required = true)
    private String duration;
    @XmlElement(required = true)
    private String difficulty;
    @XmlElement(required = true)
    private String equipment;
    @XmlElement(required = true)
    private String muscle;
    @XmlElement(required = true)
    private String calories;
    @XmlElement(required = true)
    private String instruction;
    @XmlElement(required = true)
    private String remark;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedWorkout.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedWorkout() {}

    /**
     * Constructs an {@code XmlAdaptedWorkout} with the given workout details.
     */
    public XmlAdaptedWorkout(String name, String type, String duration, String difficulty,
                             String equipment, String muscle, String calories,
                             String instruction, List<XmlAdaptedTag> tagged, String remark) {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.difficulty = difficulty;
        this.equipment = equipment;
        this.muscle = muscle;
        this.calories = calories;
        this.instruction = instruction;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Workout into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedWorkout
     */
    public XmlAdaptedWorkout(Workout source) {
        name = source.getName().fullName;
        type = source.getType().fullType;
        duration = source.getDuration().fullDuration;
        difficulty = source.getDifficulty().fullDifficulty;
        equipment = source.getEquipment().fullEquipment;
        muscle = source.getMuscle().fullMuscle;
        calories = source.getCalories().fullCalories;
        instruction = source.getInstruction().fullInstruction;
        remark = source.getRemark() == null ? "" : source.getRemark().fullRemark;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted workout object into the model's Workout object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Workout
     */
    public Workout toModelType() throws IllegalValueException {
        final List<Tag> workoutTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            workoutTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_TYPE_CONSTRAINTS);
        }
        final Type modelType = new Type(type);

        if (duration == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Duration.class.getSimpleName()));
        }
        if (!Duration.isValidDuration(duration)) {
            throw new IllegalValueException(Duration.MESSAGE_DURATION_CONSTRAINTS);
        }
        final Duration modelDuration = new Duration(duration);

        if (difficulty == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Difficulty.class.getSimpleName()));
        }
        if (!Difficulty.isValidDifficulty(difficulty)) {
            throw new IllegalValueException(Difficulty.MESSAGE_DIFFICULTY_CONSTRAINTS);
        }
        final Difficulty modelDifficulty = new Difficulty(difficulty);

        if (equipment == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Equipment.class.getSimpleName()));
        }
        if (!Equipment.isValidEquipment(equipment)) {
            throw new IllegalValueException(Equipment.MESSAGE_EQUIPMENT_CONSTRAINTS);
        }
        final Equipment modelEquipment = new Equipment(equipment);

        if (muscle == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Muscle.class.getSimpleName()));
        }
        if (!Muscle.isValidMuscle(muscle)) {
            throw new IllegalValueException(Muscle.MESSAGE_MUSCLE_CONSTRAINTS);
        }
        final Muscle modelMuscle = new Muscle(muscle);

        if (calories == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Calories.class.getSimpleName()));
        }
        if (!Calories.isValidCalories(calories)) {
            throw new IllegalValueException(Calories.MESSAGE_CALORIES_CONSTRAINTS);
        }
        final Calories modelCalories = new Calories(calories);

        //

        //if (remark == null) {

        //throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,

        //Remark.class.getSimpleName()));

        //}

        if (!Remark.isValidRemark(remark)) {
            throw new IllegalValueException(Remark.MESSAGE_REMARK_CONSTRAINTS);
        }
        final Remark modelRemark = new Remark(remark);

        if (instruction == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Instruction.class.getSimpleName()));
        }
        // no invalid instruction

        final Instruction modelInstruction = new Instruction(instruction);
        final Set<Tag> modelTags = new HashSet<>(workoutTags);
        return new Workout(modelName, modelType, modelDuration, modelDifficulty, modelEquipment,
                modelMuscle, modelCalories, modelInstruction, modelTags, modelRemark);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedWorkout)) {
            return false;
        }

        XmlAdaptedWorkout otherWorkout = (XmlAdaptedWorkout) other;
        return Objects.equals(name, otherWorkout.name)
                && Objects.equals(type, otherWorkout.type)
                && Objects.equals(duration, otherWorkout.duration)
                && Objects.equals(difficulty, otherWorkout.difficulty)
                && Objects.equals(equipment, otherWorkout.equipment)
                && Objects.equals(muscle, otherWorkout.muscle)
                && Objects.equals(calories, otherWorkout.calories)
                && Objects.equals(instruction, otherWorkout.instruction)
                && tagged.equals(otherWorkout.tagged)
                && Objects.equals(remark, otherWorkout.remark);
    }
}
