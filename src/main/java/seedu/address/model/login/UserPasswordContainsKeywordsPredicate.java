package seedu.address.model.login;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code LoginDetails}'s {@code UserPassword} matches the input password given.
 */
public class UserPasswordContainsKeywordsPredicate implements Predicate<LoginDetails> {

    private List<String> keywords;

    public UserPasswordContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(LoginDetails loginDetails) {
        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsWordCheckCase(loginDetails.getUserPassword().fullUserPassword, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UserPasswordContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((UserPasswordContainsKeywordsPredicate) other).keywords)); // state check
    }
}
