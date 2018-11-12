package seedu.address.model.workout;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Workout}'s {@code Type} matches any of the keywords given.
 */
public class TypePredicate implements Predicate<Workout> {
    private final List<String> keywords;

    public TypePredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Workout workout) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getType().fullType, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TypePredicate // instanceof handles nulls
                && keywords.equals(((TypePredicate) other).keywords)); // state check
    }

}
