package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Person> {
    private List<String> keywords = new ArrayList<>();
    private String keyword = "";

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }
    public PhoneContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        if (!keyword.isEmpty()) {
            return keyword.equals(person.getPhone().value);
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (!keyword.isEmpty()) {
            return other == this // short circuit if same object
                    || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                    && keyword.equals(((PhoneContainsKeywordsPredicate) other).keyword)); // state check
        }

        return other == this // short circuit if same object
                || (other instanceof PhoneContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PhoneContainsKeywordsPredicate) other).keywords)); // state check
    }

}
