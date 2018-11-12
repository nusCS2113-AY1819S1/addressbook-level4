package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code DateOfBirth} matches any of the keywords given.
 */
public class DateOfBirthContainsKeywordsPredicate implements Predicate<Person> {
    private final String keyword;

    public DateOfBirthContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        return keyword.equals(person.getDateOfBirth().value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DateOfBirthContainsKeywordsPredicate // instanceof handles nulls
                && keyword.equals(((DateOfBirthContainsKeywordsPredicate) other).keyword)); // state check
    }
}
