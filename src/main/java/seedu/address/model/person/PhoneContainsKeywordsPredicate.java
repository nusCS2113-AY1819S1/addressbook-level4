package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    public PhoneContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return keyword.equals(person.getPhone().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((PhoneContainsKeywordsPredicate) other).keyword)); // state check
    }

}
