package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;


import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Workout;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class WorkoutBuilder {

    public static final String DEFAULT_NAME = "commando";
    public static final String DEFAULT_TYPE = "strengh, cardio";
    public static final String DEFAULT_DURATION = "20m";
    public static final String DEFAULT_DIFFICULTY = "Advanced";
    public static final String DEFAULT_EQUIPMENT = "dumbbell";
    public static final String DEFAULT_MUSCLE = "bicep, tricep";
    public static final String DEFAULT_CALORIES = "150";
    public static final String DEFAULT_INSTRUCTION = "set 1: bicep curl reps:4-6 set 2: tricep extension reps:4-6 tag/heavy";

    private Name name;
    private Type type;
    private Duration duration;
    private Difficulty difficulty;
    private Equipment equipment;
    private Muscle muscle;
    private Calories calories;
    private Instruction instruction;
    private Set<Workout> workouts;
    private Set<Tag> tags;



    public WorkoutBuilder() {
        name = new Name(DEFAULT_NAME);
        type = new Type(DEFAULT_TYPE);
        duration = new Duration(DEFAULT_DURATION);
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        equipment = new Equipment(DEFAULT_EQUIPMENT);
        muscle = new Muscle(DEFAULT_MUSCLE);
        calories = new Calories(DEFAULT_CALORIES);
        instruction = new Instruction(DEFAULT_INSTRUCTION);
        workouts = new HashSet<>();
        tags = new HashSet<>();
    }

    /**
     * Initializes the WorkoutBuilder with the data of {@code workoutToCopy}.
     */
    public WorkoutBuilder(Workout workoutToCopy) {
        name = workoutToCopy.getName();
        type = workoutToCopy.getType();
        duration = workoutToCopy.getDuration();
        difficulty = workoutToCopy.getDifficulty();
        equipment = workoutToCopy.getEquipment();
        muscle = workoutToCopy.getMuscle();
        calories = workoutToCopy.getCalories();
        instruction = workoutToCopy.getInstruction();

        tags = new HashSet<>(workoutToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Workout} that we are building.
     */
    public WorkoutBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

      /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Workout} that we are building.
     */
    public WorkoutBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }


    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */

    public WorkoutBuilder withType(String type) {
        this.type = new Type(type);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */

    public WorkoutBuilder withDuration(String duration) {
        this.duration = new Duration(duration);
        return this;
    }


    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */

    public WorkoutBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */

    public WorkoutBuilder withEquipment(String equipment) {
        this.equipment = new Equipment(equipment);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */

    public WorkoutBuilder withMuscle(String muscle) {
        this.muscle = new Muscle(muscle);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */

    public WorkoutBuilder withCalories(String calories) {
        this.calories = new Calories(calories);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */

    public WorkoutBuilder withInstruction(String instruction) {
        this.instruction = new Instruction(instruction);
        return this;
    }





    public Workout build() {
        return new Workout(name, type, duration, difficulty, equipment, muscle, calories, instruction, tags);
    }

}
