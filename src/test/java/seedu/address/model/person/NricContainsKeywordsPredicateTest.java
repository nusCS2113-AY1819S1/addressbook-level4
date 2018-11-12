package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author chokxy
public class NricContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NricContainsKeywordsPredicate firstPredicate =
                new NricContainsKeywordsPredicate(firstPredicateKeywordList);
        NricContainsKeywordsPredicate secondPredicate =
                new NricContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NricContainsKeywordsPredicate firstPredicateCopy =
                new NricContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nricContainsKeywords_returnsTrue() {

        // One keyword
        NricContainsKeywordsPredicate predicate =
                new NricContainsKeywordsPredicate(Collections.singletonList("S9123456F"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S9123456F").build()));

        // Multiple keywords
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("S9123456F", "T9876543J"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S9123456F").build()));
        assertTrue(predicate.test(new PersonBuilder().withNric("T9876543J").build()));

        // Only one matching keyword
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("S9123456F", "T9876543J"));
        assertTrue(predicate.test(new PersonBuilder().withNric("S9123456F").build()));
    }

    @Test
    public void test_nricDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NricContainsKeywordsPredicate predicate =
                new NricContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withNric("S9123456F").build()));

        // Non-matching keyword
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("T9876543J"));
        assertFalse(predicate.test(new PersonBuilder().withNric("S9123456F").build()));

        // Keywords match name, phone and email but does not match address
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withNric("S9123456F").build()));
    }
}
