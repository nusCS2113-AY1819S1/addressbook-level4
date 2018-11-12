package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code EmployeeId} matches any of the keyword(s) given
 * or matches the keyword given.
 */
public class EmployeeIdContainsKeywordsPredicate implements Predicate<Person> {
    private List<String> keywords = new ArrayList<>();
    private String keyword = "";

    public EmployeeIdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }
    public EmployeeIdContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        if (!keyword.isEmpty()) {
            return keyword.equals(person.getEmployeeId().value);
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getEmployeeId().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (!keyword.isEmpty()) {
            return other == this // short circuit if same object
                    || (other instanceof EmployeeIdContainsKeywordsPredicate // instanceof handles nulls
                    && keyword.equals(((EmployeeIdContainsKeywordsPredicate) other).keyword)); // state check
        }

        return other == this // short circuit if same object
                || (other instanceof EmployeeIdContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EmployeeIdContainsKeywordsPredicate) other).keywords)); // state check
    }

}
