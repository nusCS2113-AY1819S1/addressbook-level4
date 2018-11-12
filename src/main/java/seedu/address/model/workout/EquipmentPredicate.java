package seedu.address.model.workout;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Workout}'s {@code Equipment} matches any of the keywords given.
 */
public class EquipmentPredicate implements Predicate<Workout> {
    private final List<String> keywords;

    public EquipmentPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Workout workout) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(workout.getEquipment().fullEquipment, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EquipmentPredicate // instanceof handles nulls
                && keywords.equals(((EquipmentPredicate) other).keywords)); // state check
    }
}
