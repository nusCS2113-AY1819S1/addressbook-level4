package seedu.address.model.account;

import java.util.function.Predicate;

/**
 * Tests that a {@code Account}'s {@code Username} matches any of the keywords given.
 */
public class UsernameMatchPredicate implements Predicate<Account> {
    private final String name;

    public UsernameMatchPredicate(String name) {
        this.name = name;
    }

    @Override
    public boolean test(Account account) {
        return name.equals(account.getUsername().toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UsernameMatchPredicate // instanceof handles nulls
                && name.equals(((UsernameMatchPredicate) other).name)); // state check
    }

}
