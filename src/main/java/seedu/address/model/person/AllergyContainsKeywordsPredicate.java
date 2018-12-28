package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.medhistory.MedHistory;

//@@author chokxy
/**
 * Tests that a {@code Person}'s {@code BloodType} matches any of the keyword given.
 */
public class AllergyContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public AllergyContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests that a {@code Person}'s {@code BloodType} matches any of the keyword given.
     */
    public boolean testAllergy(Person person) {
        //List<MedHistory> medHistory = new ArrayList<>(person.getMedHistory());

        for (MedHistory medHistory: person.getMedHistory()) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(medHistory.getAllergy().get().toString(),
                            keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test (Person person) {
        return testAllergy(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AllergyContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AllergyContainsKeywordsPredicate) other).keywords)); // state check
    }

}
