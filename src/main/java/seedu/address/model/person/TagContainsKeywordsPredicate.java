package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

//@@author chokxy
/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keyword given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests that a {@code Person}'s {@code Tag} matches any of the keyword given.
     */
    public boolean testTags(Person person) {
        // List<Tag> tags = new ArrayList<>(person.getTags());

        for (Tag tags: person.getTags()) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tags.tagName, keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test (Person person) {
        return testTags(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
