package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "99334455";
        String secondPredicateKeyword = "99334466";

        PhoneContainsKeywordsPredicate firstPredicate = new PhoneContainsKeywordsPredicate(firstPredicateKeyword);
        PhoneContainsKeywordsPredicate secondPredicate = new PhoneContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeyword_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate("99334455");
        assertTrue(predicate.test(new PersonBuilder().withPhone("99334455").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withPhone("99334455").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate("99334466");
        assertFalse(predicate.test(new PersonBuilder().withPhone("99334455").build()));
    }
}
