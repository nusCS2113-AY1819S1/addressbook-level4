package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.medicalreport.MedicalReport;

//@@author chokxy
/**
 * Tests that a {@code Person}'s {@code Medical Report Information} matches any of the keyword given.
 */
public class InfoContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public InfoContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Tests that a {@code Person}'s {@code Medical Report Information} matches any of the keyword given.
     */
    public boolean testInfo(Person person) {
        //List<MedicalReport> medicalReports = new ArrayList<>(person.getMedicalReports());

        for (MedicalReport medicalReports: person.getMedicalReports()) {
            if (keywords.stream()
                    .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(medicalReports.getInformation()
                            .fullInformation, keyword))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean test (Person person) {
        return testInfo(person);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InfoContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((InfoContainsKeywordsPredicate) other).keywords)); // state check
    }

}
