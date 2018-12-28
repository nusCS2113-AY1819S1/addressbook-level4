package seedu.address.model.medicalreport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.person.InfoContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.ReportBuilder;

//@@author chokxy
public class InfoContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        InfoContainsKeywordsPredicate firstPredicate =
                new InfoContainsKeywordsPredicate(firstPredicateKeywordList);
        InfoContainsKeywordsPredicate secondPredicate =
                new InfoContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        InfoContainsKeywordsPredicate firstPredicateCopy =
                new InfoContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_infoContainsKeywords_returnsTrue() {
        // One keyword
        InfoContainsKeywordsPredicate predicate =
                new InfoContainsKeywordsPredicate(Collections.singletonList("Sars"));
        assertTrue(predicate.test(new PersonBuilder().withMedicalReports(new ReportBuilder()
                .withInformation("Sars").build()).build()));

        // Multiple keywords
        predicate = new InfoContainsKeywordsPredicate(Arrays.asList("Sars", "H1N1"));
        assertTrue(predicate.test(new PersonBuilder().withMedicalReports(new ReportBuilder()
                .withInformation("Sars H1N1").build()).build()));

        // Only one matching keyword
        predicate = new InfoContainsKeywordsPredicate(Arrays.asList("H1N1", "Sinus"));
        assertTrue(predicate.test(new PersonBuilder().withMedicalReports(new ReportBuilder()
                .withInformation("Sars H1N1").build()).build()));

        // Mixed-case keywords
        predicate = new InfoContainsKeywordsPredicate(Arrays.asList("saRs", "h1n1"));
        assertTrue(predicate.test(new PersonBuilder().withMedicalReports(new ReportBuilder()
                .withInformation("Sars H1N1").build()).build()));

        //Mixed-case keyword
        predicate = new InfoContainsKeywordsPredicate(Arrays.asList("saRs"));
        assertTrue(predicate.test(new PersonBuilder().withMedicalReports(new ReportBuilder()
                .withInformation("Sars").build()).build()));
    }

    @Test
    public void test_infoDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        InfoContainsKeywordsPredicate predicate =
                new InfoContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMedicalReports(new ReportBuilder()
                .withInformation("Sars").build()).build()));

        // Non-matching keyword
        predicate = new InfoContainsKeywordsPredicate(Arrays.asList("Sinus"));
        assertFalse(predicate.test(new PersonBuilder().withMedicalReports(new ReportBuilder()
                .withInformation("Sars").build()).build()));

        // Keywords match name, phone and email but does not match address
        predicate = new InfoContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMedicalReports(new ReportBuilder()
                        .withInformation("Sars").build()).build()));
    }
}
