package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.HammingDistanceUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final int SIMILARITY = 2;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String tokens[] = person.getName().fullName.split("\\s+");

        for (int i = 0; i < keywords.size(); i++) {
            for (int j = 0; j < tokens.length; j++) {
                HammingDistanceUtil myHammingDistance = new HammingDistanceUtil(keywords.get(i), tokens[j]);
                if (myHammingDistance.getDistance() < SIMILARITY) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
