package seedu.address.testutil;

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

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditWorkoutDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.workout.Remark;
import seedu.address.model.workout.Workout;

/**
 * A utility class for Workout.
 */
public class WorkoutUtil {

    /**
     * Returns an add command string for adding the {@code workout}.
     */
    public static String getAddCommand(Workout workout) {
        return AddCommand.COMMAND_WORD + " " + getWorkoutDetails(workout);
    }

    /**
     * Returns the part of command string for the given {@code workout}'s details.
     */
    public static String getWorkoutDetails(Workout workout) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + workout.getName().fullName + " ");
        sb.append(PREFIX_TYPE + workout.getType().fullType + " ");
        sb.append(PREFIX_DURATION + workout.getDuration().fullDuration + " ");
        sb.append(PREFIX_DIFFICULTY + workout.getDifficulty().fullDifficulty + " ");
        sb.append(PREFIX_EQUIPMENT + workout.getEquipment().fullEquipment + " ");
        sb.append(PREFIX_MUSCLE + workout.getMuscle().fullMuscle + " ");
        sb.append(PREFIX_CALORIES + workout.getCalories().fullCalories + " ");
        sb.append(PREFIX_INSTRUCTION + workout.getInstruction().fullInstruction + " ");
        workout.getTags().stream().forEach(s -> sb.append(PREFIX_TAG + s.tagName + " "));
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditWorkoutDescriptor}'s details.
     */
    public static String getEditWorkoutDescriptorDetails(EditWorkoutDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getType().ifPresent(type -> sb.append(PREFIX_TYPE).append(type.fullType).append(" "));
        descriptor.getDuration().ifPresent(duration -> sb.append(PREFIX_DURATION).append(duration.fullDuration)
                .append(" "));
        descriptor.getDifficulty().ifPresent(difficulty -> sb.append(PREFIX_DIFFICULTY)
                .append(difficulty.fullDifficulty).append(" "));
        descriptor.getEquipment().ifPresent(equipment -> sb.append(PREFIX_EQUIPMENT).append(equipment.fullEquipment)
                .append(" "));
        descriptor.getMuscle().ifPresent(muscle -> sb.append(PREFIX_MUSCLE).append(muscle.fullMuscle).append(" "));
        descriptor.getCalories().ifPresent(calories -> sb.append(PREFIX_CALORIES).append(calories.fullCalories)
                .append(" "));
        descriptor.getInstruction().ifPresent(instruction -> sb.append(PREFIX_INSTRUCTION)
                .append(instruction.fullInstruction).append(" "));

        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param remark
     * @return
     */
    public static String getRemarkWorkoutDetails(Remark remark) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_REMARK).append(remark.fullRemark);
        return sb.toString();
    }
}
