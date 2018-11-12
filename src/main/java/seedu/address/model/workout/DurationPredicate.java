package seedu.address.model.workout;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Workout}'s {@code Duration} matches any of the duration given.
 */
public class DurationPredicate implements Predicate<Workout> {
    private final List<String> keywords;

    public DurationPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Workout workout) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getDuration().fullDuration, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DurationPredicate // instanceof handles nulls
                && keywords.equals(((DurationPredicate) other).keywords)); // state check
    }
}
