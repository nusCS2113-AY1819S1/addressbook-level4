package seedu.address.model.expenditureinfo;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Expenditure}'s {@code Name} matches any of the keywords given.
 */
public class ExpenditureNameContainsKeywordsPredicate implements Predicate<Expenditure> {
    private final List<String> keywords;

    public ExpenditureNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Expenditure expenditure) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(expenditure.getDescription().descriptionName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenditureNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ExpenditureNameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
