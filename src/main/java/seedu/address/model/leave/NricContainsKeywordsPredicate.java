package seedu.address.model.leave;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author Hafizuddin-NUS
/**
 * Tests that a {@code Leave}'s {@code Name} matches any of the keywords given.
 */
public class NricContainsKeywordsPredicate implements Predicate<Leave> {

    private final List<String> keywords;

    public NricContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Leave leave) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(leave.getEmployeeId().nric, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NricContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NricContainsKeywordsPredicate) other).keywords)); // state check
    }
}
