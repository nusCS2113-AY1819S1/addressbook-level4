package seedu.address.model.item;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Item}'s {@code Tags} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Item> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate (List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Item item) {
        return keywords.stream()
                .anyMatch(keyword -> item.getTags().toString().toLowerCase().contains(keyword.toLowerCase()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
