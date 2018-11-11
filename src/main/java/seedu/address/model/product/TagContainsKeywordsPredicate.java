package seedu.address.model.product;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Product}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Product> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Product product) {
        String tagString = " ";
        for (Tag tag : product.getTags()) {
            tagString = tagString + tag.toString();
        }

        final String finalTagString = tagString.replace("[", "").replace("]", " ");

        return keywords.stream()
                .anyMatch(keyword
                    -> StringUtil.containsWordIgnoreCase(finalTagString, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagContainsKeywordsPredicate
                && keywords.equals(((TagContainsKeywordsPredicate) other).keywords));
    }

}
