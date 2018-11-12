package seedu.address.model.schedule;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Schedule}'s {@code Name} matches any of the Id given.
 */
public class EmployeeIdScheduleContainsKeywordsPredicate implements Predicate <Schedule> {
    private final List<String> keywords;

    public EmployeeIdScheduleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Schedule schedule) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(schedule.getEmployeeId().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeIdScheduleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EmployeeIdScheduleContainsKeywordsPredicate) other).keywords)); // state check
    }

}
