package seedu.address.model.distributor;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Distributor}'s {@code Tag} matches any of the keywords given.
 */
public class DTagContainsKeywordsPredicate implements Predicate<Distributor> {
    private final List<String> keywords;

    public DTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Distributor distributor) {
        return keywords.stream()
                .anyMatch(keyword
                        -> StringUtil.containsWordIgnoreCase(distributor.getTags().toString()
                        .replace("[", "").replace("]", ""), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.distributor.DTagContainsKeywordsPredicate
                && keywords.equals(((seedu.address.model.distributor.DTagContainsKeywordsPredicate) other).keywords));
    }

}
