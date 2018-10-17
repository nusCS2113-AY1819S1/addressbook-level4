package seedu.recruit.model.company;

import java.util.List;
import java.util.function.Predicate;

import seedu.recruit.commons.util.StringUtil;

/**
 * Tests that a {@code Candidate}'s {@code Name} matches any of the keywords given.
 */
public class CompanyNameContainsKeywordsPredicate implements Predicate<Company> {
    private final List<String> keywords;

    public CompanyNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Company company) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(company.getCompanyName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.recruit.model.company.CompanyNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((seedu.recruit.model.company.CompanyNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}

