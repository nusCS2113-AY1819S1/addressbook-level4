package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "first@exam.com";
        String secondPredicateKeyword = "second@exam.com";

        EmailContainsKeywordsPredicate firstPredicate = new EmailContainsKeywordsPredicate(firstPredicateKeyword);
        EmailContainsKeywordsPredicate secondPredicate = new EmailContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy = new EmailContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeyword_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate("Alice@example.com");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Mixed-case keyword
        predicate = new EmailContainsKeywordsPredicate("ALIce@examPLE.cOm");
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));
    }

    @Test
    public void test_emailDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate("Carol@example.com");
        assertFalse(predicate.test(new PersonBuilder().withEmail("Alice@example.com").build()));
    }
}
