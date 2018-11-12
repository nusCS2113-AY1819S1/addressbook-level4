package seedu.address.model.workout;

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

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Workout} contains the given parameter.
 */
public class WorkoutContainsParameterPredicate implements Predicate<Workout> {
    private final List<Parameter> parameters;

    public WorkoutContainsParameterPredicate(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean test(Workout workout) {
        Prefix prefix = parameters.get(0).getPrefix();
        if (prefix.equals(PREFIX_NAME)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getName().fullName,
                            keyword.getValue()));
        } else if (prefix.equals(PREFIX_TYPE)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getType().fullType,
                            keyword.getValue()));
        } else if (prefix.equals(PREFIX_DURATION)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getDuration().fullDuration,
                            keyword.getValue()));
        } else if (prefix.equals(PREFIX_DIFFICULTY)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getDifficulty().fullDifficulty,
                            keyword.getValue()));
        } else if (prefix.equals(PREFIX_EQUIPMENT)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getEquipment().fullEquipment,
                            keyword.getValue()));
        } else if (prefix.equals(PREFIX_MUSCLE)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getMuscle().fullMuscle,
                            keyword.getValue()));
        } else if (prefix.equals(PREFIX_CALORIES)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getCalories().fullCalories,
                            keyword.getValue()));
        } else if (prefix.equals(PREFIX_INSTRUCTION)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getInstruction().fullInstruction,
                            keyword.getValue()));
        } else if (prefix.equals(PREFIX_TAG)) {
            Set<Tag> tags = workout.getTags();
            boolean hasValue = false;
            for (Tag tag : tags) {
                if (tag.tagName.contains(parameters.get(0).getValue())) {
                    hasValue = true;
                }
            }
            return hasValue;
        } else if (prefix.equals(PREFIX_REMARK)) {
            return parameters.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getRemark().fullRemark,
                            keyword.getValue()));
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WorkoutContainsParameterPredicate // instanceof handles nulls
                && parameters.equals(((WorkoutContainsParameterPredicate) other).parameters)); // state check
    }

}
