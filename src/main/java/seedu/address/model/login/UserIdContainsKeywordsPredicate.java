package seedu.address.model.login;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code LoginDetails}'s {@code UserId} matches the user ID given.
 */
public class UserIdContainsKeywordsPredicate implements Predicate<LoginDetails> {

    private List<String> keywords;

    public UserIdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(LoginDetails loginDetails) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(loginDetails.getUserId().fullUserId, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserIdContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((UserIdContainsKeywordsPredicate) other).keywords)); // state check
    }
}
