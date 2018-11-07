package seedu.address.model.item;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Item}'s {@code ItemName} matches any of the keywords given.
 */
public class ItemNameContainsKeywordsPredicate implements Predicate<Item> {
    private final List<String> keywords;

    public ItemNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(item.getItemName().fullItemName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ItemNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ItemNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
