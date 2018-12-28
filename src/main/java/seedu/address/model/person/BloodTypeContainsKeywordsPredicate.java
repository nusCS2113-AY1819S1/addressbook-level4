package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

//@@author chokxy
/**
 * Tests that a {@code Person}'s {@code BloodType} matches any of the keyword given.
 */
public class BloodTypeContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public BloodTypeContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getBloodType().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodTypeContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BloodTypeContainsKeywordsPredicate) other).keywords)); // state check
    }

}
