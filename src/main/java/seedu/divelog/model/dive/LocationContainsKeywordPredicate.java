package seedu.divelog.model.dive;

import java.util.List;
import java.util.function.Predicate;

import seedu.divelog.commons.util.StringUtil;

/**
 * @author arjo
 * Tests that a {@code Dives}'s {@code location} matches any of the keywords given.
 */
public class LocationContainsKeywordPredicate implements Predicate<DiveSession> {
    private final List<String> keywords;

    public LocationContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(DiveSession dive) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(dive.getLocation().getLocationName(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocationContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((LocationContainsKeywordPredicate) other).keywords)); // state check
    }

}
