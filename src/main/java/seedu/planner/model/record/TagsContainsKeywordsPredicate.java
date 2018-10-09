package seedu.planner.model.record;

import java.util.List;
import java.util.function.Predicate;

import seedu.planner.commons.util.StringUtil;
import seedu.planner.model.tag.Tag;

/**
 * Tests that a {@code Record}'s {@code Tags} matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<Record> {
    private final List<String> keywords;

    public TagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests that each of a {@code Record}'s {@code Tags} matches any of the keywords given.
     */
    public boolean testIfSetContainsString(Record record) {
        for (Tag x : record.getTags()) {
            if (keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(x.tagName, keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test(Record record) {
        return testIfSetContainsString(record);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagsContainsKeywordsPredicate) other).keywords)); // state check
    }
}
