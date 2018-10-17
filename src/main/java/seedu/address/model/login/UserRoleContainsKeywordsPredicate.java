package seedu.address.model.login;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code LoginDetails}'s {@code UserRole} matches the input role given.
 */
public class UserRoleContainsKeywordsPredicate implements Predicate<LoginDetails> {

    private List<String> keywords;

    public UserRoleContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(LoginDetails loginDetails) {
        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(loginDetails.getUserRole().fullUserRole, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserRoleContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((UserRoleContainsKeywordsPredicate) other).keywords)); // state check
    }
}
