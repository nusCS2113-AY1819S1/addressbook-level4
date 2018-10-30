package seedu.recruit.model.candidate;

import java.util.List;
import java.util.HashMap;
import java.util.function.Predicate;

import seedu.recruit.commons.util.StringUtil;

/**
 * Tests that any of {@code Candidate}'s details matches any of the keywords given.
 */
public class CandidateContainsKeywordsPredicate implements Predicate<Candidate> {
    private final HashMap<String, List<String>> keywords;

    public CandidateContainsKeywordsPredicate(HashMap<String, List<String>> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Candidate candidate) {
        return ((keywords.containsKey("Name")) && (keywords.get("Name").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getName().fullName, keyword))))
                || ((keywords.containsKey("Gender")) && (keywords.get("Gender").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getGender().value, keyword))))
                || ((keywords.containsKey("Age")) && (keywords.get("Age").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getAge().value, keyword))))
                || ((keywords.containsKey("Phone")) && (keywords.get("Phone").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getPhone().value, keyword))))
                || ((keywords.containsKey("Email")) && (keywords.get("Email").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getEmail().value, keyword))))
                || ((keywords.containsKey("Address")) && (keywords.get("Address").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getAddress().value, keyword))))
                || ((keywords.containsKey("Job")) && (keywords.get("Job").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getJob().value, keyword))))
                || ((keywords.containsKey("Education")) && (keywords.get("Education").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getEducation().value, keyword))))
                || ((keywords.containsKey("Salary")) && (keywords.get("Salary").stream().anyMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(candidate.getSalary().value, keyword))));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CandidateContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((CandidateContainsKeywordsPredicate) other).keywords)); // state check
    }

}
