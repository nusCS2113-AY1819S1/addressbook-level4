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
public class CountryContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CountryContainsKeywordsPredicate firstPredicate =
                new CountryContainsKeywordsPredicate(firstPredicateKeywordList);
        CountryContainsKeywordsPredicate secondPredicate =
                new CountryContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CountryContainsKeywordsPredicate firstPredicateCopy =
                new CountryContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_countryContainsKeywords_returnsTrue() {

        // One keyword
        CountryContainsKeywordsPredicate predicate =
                new CountryContainsKeywordsPredicate(Collections.singletonList("Japan"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withPrevCountry("Japan").build()).build()));

        // Multiple keywords
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("Japan", "Uganda"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withPrevCountry("Japan Uganda").build()).build()));

        // Only one matching keyword
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("USA", "Malaysia"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withPrevCountry("Japan USA").build()).build()));

        // Mixed-case keywords
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("JApaN", "UGAnda"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withPrevCountry("Japan Uganda").build()).build()));

        //Mixed-case keyword
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("JApaN"));
        assertTrue(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withPrevCountry("Japan").build()).build()));
    }

    @Test
    public void test_countryDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CountryContainsKeywordsPredicate predicate =
                new CountryContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withPrevCountry("Japan").build()).build()));

        // Non-matching keyword
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("Korea"));
        assertFalse(predicate.test(new PersonBuilder().withMedHistories(new MedHistoryBuilder()
                .withPrevCountry("Japan").build()).build()));

        // Keywords match name, phone and email but does not match address
        predicate = new CountryContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withMedHistories(new MedHistoryBuilder()
                        .withPrevCountry("Japan").build()).build()));
    }
}
