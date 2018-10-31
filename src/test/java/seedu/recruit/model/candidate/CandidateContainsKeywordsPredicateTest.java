package seedu.recruit.model.candidate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.CandidateContainsKeywordsPredicateBuilder;

public class CandidateContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        HashMap<String, List<String>> firstPredicateKeywordList =
                new CandidateContainsKeywordsPredicateBuilder("n/first").getKeywordsList();
        HashMap<String, List<String>> secondPredicateKeywordList =
                new CandidateContainsKeywordsPredicateBuilder("n/first n/second").getKeywordsList();

        CandidateContainsKeywordsPredicate firstPredicate = new
                CandidateContainsKeywordsPredicate(firstPredicateKeywordList);
        CandidateContainsKeywordsPredicate secondPredicate = new
                CandidateContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CandidateContainsKeywordsPredicate firstPredicateCopy = new
                CandidateContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different candidate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    @Ignore
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        CandidateContainsKeywordsPredicate predicate =
                new CandidateContainsKeywordsPredicateBuilder("n/Alice").getCandidatePredicate();
        assertTrue(predicate.test(new CandidateBuilder().withName("n/Alice n/Bob").build()));

        // Multiple keywords
        predicate = new CandidateContainsKeywordsPredicateBuilder("n/Alice n/Bob").getCandidatePredicate();
        assertTrue(predicate.test(new CandidateBuilder().withName("n/Alice n/Bob").build()));

        // Only one matching keyword
        predicate = new CandidateContainsKeywordsPredicateBuilder("n/Bob n/Carol").getCandidatePredicate();
        assertTrue(predicate.test(new CandidateBuilder().withName("n/Alice n/Carol").build()));

        // Mixed-case keywords
        predicate = new CandidateContainsKeywordsPredicateBuilder("n/aLIce n/bOB").getCandidatePredicate();
        assertTrue(predicate.test(new CandidateBuilder().withName("n/Alice n/Bob").build()));
    }

    @Test
    @Ignore
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CandidateContainsKeywordsPredicate predicate =
                new CandidateContainsKeywordsPredicate((HashMap<String, List<String>> ) Collections.EMPTY_MAP);
        assertFalse(predicate.test(new CandidateBuilder().withName("n/Alice").build()));

        // Non-matching keyword
        predicate = new CandidateContainsKeywordsPredicateBuilder("n/Carol").getCandidatePredicate();
        assertFalse(predicate.test(new CandidateBuilder().withName("n/Alice n/Bob").build()));

        // Keywords match phone, email and recruit, but does not match name
        predicate = new CandidateContainsKeywordsPredicateBuilder(
                "p/12345 e/alice@email.com a/Main Street").getCandidatePredicate();
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
