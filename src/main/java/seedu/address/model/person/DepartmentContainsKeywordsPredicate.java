package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Department} matches any of the keywords given.
 */
public class DepartmentContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public DepartmentContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getDepartment().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DepartmentContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DepartmentContainsKeywordsPredicate) other).keywords)); // state check
    }

}
