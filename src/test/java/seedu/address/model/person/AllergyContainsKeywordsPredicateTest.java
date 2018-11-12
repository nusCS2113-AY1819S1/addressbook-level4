package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.MedHistoryBuilder;
import seedu.address.testutil.PersonBuilder;

//@@author chokxy
public class AllergyContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AllergyContainsKeywordsPredicate firstPredicate =
                new AllergyContainsKeywordsPredicate(firstPredicateKeywordList);
        AllergyContainsKeywordsPredicate secondPredicate =
                new AllergyContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AllergyContainsKeywordsPredicate firstPredicateCopy =
                new AllergyContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_allergyContainsKeywords_returnsTrue() {

        // One keyword
        AllergyContainsKeywordsPredicate predicate =
                new AllergyContainsKeywordsPredicate(Collections.singletonList("Alcohol"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Alcohol").build()).build()));

        // Multiple keywords
        predicate = new AllergyContainsKeywordsPredicate(Arrays.asList("Alcohol", "Nuts"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Alcohol").build()).build()));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Nuts").build()).build()));

        // Only one matching keyword
        predicate = new AllergyContainsKeywordsPredicate(Arrays.asList("Seafood", "Sinus"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Seafood").build()).build()));

        // Mixed-case keywords
        predicate = new AllergyContainsKeywordsPredicate(Arrays.asList("AlCoHOL", "NuTs"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Alcohol").build()).build()));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Nuts").build()).build()));

        //Mixed-case keyword
        predicate = new AllergyContainsKeywordsPredicate(Arrays.asList("AlCoHOL"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Alcohol").build()).build()));
    }

    @Test
    public void test_allergyDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AllergyContainsKeywordsPredicate predicate =
                new AllergyContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Alcohol").build()).build()));

        // Non-matching keyword
        predicate = new AllergyContainsKeywordsPredicate(Arrays.asList("Nuts"));
        assertFalse(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withAllergy("Alcohol").build()).build()));

        // Keywords match name, phone and email but does not match address
        predicate = new AllergyContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMedHistories(new MedHistoryBuilder()
                        .withAllergy("Alcohol").build()).build()));
    }
}
