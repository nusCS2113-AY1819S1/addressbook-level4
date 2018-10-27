package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class KpiContainsKeywordPredicate implements Predicate<Person> {

    private final List<String> keywords;

    public KpiContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (person.getKpi().value != null) {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getKpi().value, keyword));
        } else {
            return keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase("", keyword));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof KpiContainsKeywordPredicate // instanceof handles nulls
                && keywords.equals(((KpiContainsKeywordPredicate) other).keywords)); // state check
    }

}
