package seedu.address.model.workout;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Workout in the workout book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Workout {

    // Identity fields
    private final Name name;

    // Data fields
    private final Type type;
    private final Difficulty difficulty;
    private final Duration duration;
    private final Equipment equipment;
    private final Muscle muscle;
    private final Calories calories;
    private final Instruction instruction;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Workout(Name name, Type type, Duration duration, Difficulty difficulty, Equipment equipment,
                  Muscle muscle, Calories calories, Instruction instruction, Set<Tag> tags, Remark remark) {
        requireAllNonNull(name, type, duration, difficulty, equipment, muscle, calories, instruction, tags);
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.difficulty = difficulty;
        this.equipment = equipment;
        this.muscle = muscle;
        this.calories = calories;
        this.instruction = instruction;
        this.tags.addAll(tags);
        this.remark = remark;

    }

    public Name getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Duration getDuration() {
        return duration;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Muscle getMuscle() {
        return muscle;
    }

    public Calories getCalories() {
        return calories;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public Remark getRemark() {
        return remark;
    }


    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both workouts have the same name or all the other fields are the same
     * This defines a weaker notion of equality between two workouts.
     */
    public boolean isSameWorkout(Workout otherWorkout) {
        if (otherWorkout == this) {
            return true;
        }
        return otherWorkout != null
                && ((otherWorkout.getName().equals(getName()))
                || (otherWorkout.getType().equals(getType()) && otherWorkout.getDuration().equals(getDuration())
                && otherWorkout.getDifficulty().equals(getDifficulty())
                && otherWorkout.getEquipment().equals(getEquipment())
                && otherWorkout.getMuscle().equals(getMuscle()) && otherWorkout.getCalories().equals(getCalories())
                && otherWorkout.getInstruction().equals(getInstruction())));
    }

    /**
     * Returns true if both workouts have the same identity and data fields.
     * This defines a stronger notion of equality between two workouts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Workout)) {
            return false;
        }

        Workout otherWorkout = (Workout) other;
        return otherWorkout.getName().equals(getName())
                && otherWorkout.getType().equals(getType())
                && otherWorkout.getDuration().equals(getDuration())
                && otherWorkout.getDifficulty().equals(getDifficulty())
                && otherWorkout.getEquipment().equals(getEquipment())
                && otherWorkout.getMuscle().equals(getMuscle())
                && otherWorkout.getCalories().equals(getCalories())
                && otherWorkout.getInstruction().equals(getInstruction())
                && otherWorkout.getTags().equals(getTags());
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, type, duration, difficulty, equipment, muscle, calories, instruction, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Type: ")
                .append(getType())
                .append(" Duration: ")
                .append(getDuration())
                .append(" Difficulty: ")
                .append(getDifficulty())
                .append(" Equipment: ")
                .append(getEquipment())
                .append(" Muscle: ")
                .append(getMuscle())
                .append(" Calories: ")
                .append(getCalories())
                .append(" Instruction: ")
                .append(getInstruction())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
