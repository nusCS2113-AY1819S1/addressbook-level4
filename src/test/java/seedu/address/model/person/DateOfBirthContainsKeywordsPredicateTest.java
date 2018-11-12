package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class DateOfBirthContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        String firstPredicateKeyword = "01/01/1980";
        String secondPredicateKeyword = "02/02/1990";

        DateOfBirthContainsKeywordsPredicate firstPredicate =
                new DateOfBirthContainsKeywordsPredicate(firstPredicateKeyword);
        DateOfBirthContainsKeywordsPredicate secondPredicate =
                new DateOfBirthContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateOfBirthContainsKeywordsPredicate firstPredicateCopy =
                new DateOfBirthContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateOfBirthContainsKeyword_returnsTrue() {
        // One keyword
        DateOfBirthContainsKeywordsPredicate predicate = new DateOfBirthContainsKeywordsPredicate("01/01/1990");
        assertTrue(predicate.test(new PersonBuilder().withDateOfBirth("01/01/1990").build()));
    }

    @Test
    public void test_dateOfBirthDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        DateOfBirthContainsKeywordsPredicate predicate = new DateOfBirthContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withDateOfBirth("01/01/1990").build()));

        // Non-matching keyword
        predicate = new DateOfBirthContainsKeywordsPredicate("02/02/1980");
        assertFalse(predicate.test(new PersonBuilder().withDateOfBirth("01/01/1990").build()));
    }
}
