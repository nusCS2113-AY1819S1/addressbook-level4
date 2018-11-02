package seedu.recruit.model.joboffer;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import seedu.recruit.commons.util.StringUtil;

/**
 * Tests that a {@code Job}'s details matches any of the keywords given.
 */
public class JobOfferContainsKeywordsPredicate implements Predicate<JobOffer> {
    private final HashMap<String, List<String>> keywords;

    public JobOfferContainsKeywordsPredicate(HashMap<String, List<String>> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(JobOffer jobOffer) {
        return ((keywords.containsKey("CompanyName")) && (keywords.get("CompanyName").stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(jobOffer.getCompanyName().value, keyword))))
                && ((keywords.containsKey("Job")) && (keywords.get("Job").stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(jobOffer.getJob().value, keyword))))
                || ((keywords.containsKey("Age Range")) && (keywords.get("Age Range").stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(jobOffer.getAgeRange().value, keyword))))
                || ((keywords.containsKey("Education")) && (keywords.get("Education").stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(jobOffer.getEducation().value, keyword))))
                || ((keywords.containsKey("Salary")) && (keywords.get("Salary").stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(jobOffer.getSalary().value, keyword))))
                || ((keywords.containsKey("Gender")) && (keywords.get("Gender").stream().anyMatch(keyword ->
                StringUtil.containsWordIgnoreCase(jobOffer.getGender().value, keyword))));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.recruit.model.joboffer.JobOfferContainsKeywordsPredicate
                // instanceof handles nulls
                && keywords.equals(((seedu.recruit.model.joboffer.JobOfferContainsKeywordsPredicate)
                other).keywords)); // state check
    }
}

