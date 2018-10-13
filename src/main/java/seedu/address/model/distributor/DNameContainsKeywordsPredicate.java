package seedu.address.model.distributor;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Distributor}'s {@code DistributorName} matches any of the keywords given.
 */
public class DNameContainsKeywordsPredicate implements Predicate<Distributor> {
    private final List<String> keywords;

    public DNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Distributor distributor) {
        return keywords.stream()
                .anyMatch(keyword
                        -> StringUtil.containsWordIgnoreCase(distributor.getDistName().fullDistName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.distributor.DNameContainsKeywordsPredicate
                && keywords.equals(((seedu.address.model.distributor.DNameContainsKeywordsPredicate) other).keywords));
    }

}
