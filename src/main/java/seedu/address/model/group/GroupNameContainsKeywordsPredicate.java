package seedu.address.model.group;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Group}'s {@code GroupName} matches any of the keywords given.
 */
public class GroupNameContainsKeywordsPredicate implements Predicate<Group> {

    private final List<String> keywords;

    /**
     * Receives keywords to set.
     *
     * @param keywords To be set.
     */
    public GroupNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests if a group's group name matches any keyword.
     *
     * @param group Group whose name will be tested against.
     * @return Test result.
     */
    @Override
    public boolean test(Group group) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(group.getGroupName().groupName, keyword));
    }

    /**
     * Returns true if both objects have the same fields.
     *
     * @param other GroupNameContainsKeywordsPredicate to compare with.
     * @return Comparison result.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((GroupNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
