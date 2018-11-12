package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditWorkoutDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Calories;
import seedu.address.model.workout.Difficulty;
import seedu.address.model.workout.Duration;
import seedu.address.model.workout.Equipment;
import seedu.address.model.workout.Instruction;
import seedu.address.model.workout.Muscle;
import seedu.address.model.workout.Name;
import seedu.address.model.workout.Type;
import seedu.address.model.workout.Workout;

/**
 * A utility class to help with building EditWorkoutDescriptor objects.
 */
public class EditWorkoutDescriptorBuilder {

    private EditWorkoutDescriptor descriptor;

    public EditWorkoutDescriptorBuilder() {
        descriptor = new EditWorkoutDescriptor();
    }

    public EditWorkoutDescriptorBuilder(EditWorkoutDescriptor descriptor) {
        this.descriptor = new EditWorkoutDescriptor(descriptor);
    }


    /**
     * Returns an {@code EditWorkoutDescriptor} with fields containing {@code workout}'s details
     */

    public EditWorkoutDescriptorBuilder(Workout workout) {
        descriptor = new EditWorkoutDescriptor();
        descriptor.setName(workout.getName());
        descriptor.setType(workout.getType());
        descriptor.setDuration(workout.getDuration());
        descriptor.setDifficulty(workout.getDifficulty());
        descriptor.setEquipment(workout.getEquipment());
        descriptor.setMuscle(workout.getMuscle());
        descriptor.setCalories(workout.getCalories());
        descriptor.setInstruction(workout.getInstruction());
        descriptor.setTags(workout.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditWorkoutDescriptor} that we are building.
     */
    public EditWorkoutDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Type} of the {@code EditWorkoutDescriptor} that we are building.
     */

    public EditWorkoutDescriptorBuilder withType(String type) {
        descriptor.setType(new Type(type));
        return this;
    }


    /**
     * Sets the {@code Duration} of the {@code EditWorkoutDescriptor} that we are building.
     */

    public EditWorkoutDescriptorBuilder withDuration(String duration) {
        descriptor.setDuration(new Duration(duration));
        return this;
    }


    /**
     * Sets the {@code Difficulty} of the {@code EditWorkoutDescriptor} that we are building.
     */

    public EditWorkoutDescriptorBuilder withDifficulty(String difficulty) {
        descriptor.setDifficulty(new Difficulty(difficulty));
        return this;
    }

    /**
     * Sets the {@code Equipment} of the {@code EditWorkoutDescriptor} that we are building.
     */

    public EditWorkoutDescriptorBuilder withEquipment(String equipment) {
        descriptor.setEquipment(new Equipment(equipment));
        return this;
    }

    /**
     * Sets the {@code Muscle} of the {@code EditWorkoutDescriptor} that we are building.
     */

    public EditWorkoutDescriptorBuilder withMuscle(String muscle) {
        descriptor.setMuscle(new Muscle(muscle));
        return this;
    }

    /**
     * Sets the {@code Calories} of the {@code EditWorkoutDescriptor} that we are building.
     */

    public EditWorkoutDescriptorBuilder withCalories(String calories) {
        descriptor.setCalories(new Calories(calories));
        return this;
    }

    /**
     * Sets the {@code Instruction} of the {@code EditWorkoutDescriptor} that we are building.
     */

    public EditWorkoutDescriptorBuilder withInstruction(String instruction) {
        descriptor.setInstruction(new Instruction(instruction));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditWorkoutDescriptor}
     * that we are building.
     */
    public EditWorkoutDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditWorkoutDescriptor build() {
        return descriptor;
    }
}
