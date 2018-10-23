package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        int isMerged = 0;
        if (keywords.get(0).equalsIgnoreCase("merged")) {
            for (Tag tag : person.getTags()) {
                if (tag.toString().equalsIgnoreCase("[merged]")) {
                    isMerged = 1;
                }
            }
            if (isMerged == 0) {
                return true;
            }
        }
        if (keywords.get(0).equalsIgnoreCase("main")) {
            for (Tag tag : person.getTags()) {
                if (tag.toString().equalsIgnoreCase("[merged]")) {
                    return true;
                }
            }
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
