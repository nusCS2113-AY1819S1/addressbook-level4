package seedu.address.model.member;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * postalcode keyword predicate
 */
public class PostalcodeContainsKeywordPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public PostalcodeContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPostalcode().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalcodeContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((PostalcodeContainsKeywordPredicate) other).keywords)); // state check
    }

}
