package seedu.recruit.model.candidate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import seedu.recruit.testutil.CandidateBuilder;
import seedu.recruit.testutil.CandidateContainsFindKeywordsPredicateBuilder;

public class CandidateContainsFindKeywordsPredicateTest {

    @Test
    public void equals() {
        HashMap<String, List<String>> firstPredicateKeywordList =
                new CandidateContainsFindKeywordsPredicateBuilder("n/first").getKeywordsList();
        HashMap<String, List<String>> secondPredicateKeywordList =
                new CandidateContainsFindKeywordsPredicateBuilder("n/first n/second").getKeywordsList();

        CandidateContainsFindKeywordsPredicate firstPredicate = new
                CandidateContainsFindKeywordsPredicate(firstPredicateKeywordList);
        CandidateContainsFindKeywordsPredicate secondPredicate = new
                CandidateContainsFindKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CandidateContainsFindKeywordsPredicate firstPredicateCopy = new
                CandidateContainsFindKeywordsPredicate(firstPredicateKeywordList);
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
        CandidateContainsFindKeywordsPredicate predicate =
                new CandidateContainsFindKeywordsPredicateBuilder("n/Alice").getCandidatePredicate();
        assertTrue(predicate.test(new CandidateBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new CandidateContainsFindKeywordsPredicateBuilder("n/Alice n/Bob").getCandidatePredicate();
        assertTrue(predicate.test(new CandidateBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new CandidateContainsFindKeywordsPredicateBuilder("n/Bob n/Carol").getCandidatePredicate();
        assertTrue(predicate.test(new CandidateBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new CandidateContainsFindKeywordsPredicateBuilder("n/aLIce n/bOB").getCandidatePredicate();
        assertTrue(predicate.test(new CandidateBuilder().withName("Alice Bob").build()));
    }

    @Test
    @Ignore
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CandidateContainsFindKeywordsPredicate predicate =
                new CandidateContainsFindKeywordsPredicate((HashMap<String, List<String>> ) Collections.EMPTY_MAP);
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new CandidateContainsFindKeywordsPredicateBuilder("n/Carol").getCandidatePredicate();
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and recruit, but does not match name
        predicate = new CandidateContainsFindKeywordsPredicateBuilder(
                "p/12345 e/alice@email.com a/Main Street").getCandidatePredicate();
        assertFalse(predicate.test(new CandidateBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
