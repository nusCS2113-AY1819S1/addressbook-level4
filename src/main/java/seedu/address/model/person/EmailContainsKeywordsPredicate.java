package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Email} matches the keyword given.
 */
public class EmailContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    public EmailContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return keyword.equalsIgnoreCase(person.getEmail().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((EmailContainsKeywordsPredicate) other).keyword)); // state check
    }
}
