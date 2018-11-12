package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PositionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PositionContainsKeywordsPredicate firstPredicate =
                new PositionContainsKeywordsPredicate(firstPredicateKeywordList);
        PositionContainsKeywordsPredicate secondPredicate =
                new PositionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PositionContainsKeywordsPredicate firstPredicateCopy =
                new PositionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_positionContainsKeywords_returnsTrue() {
        // One keyword
        PositionContainsKeywordsPredicate predicate =
                new PositionContainsKeywordsPredicate(Collections.singletonList("Intern"));
        assertTrue(predicate.test(new PersonBuilder().withPosition("Manager Intern").build()));

        // Multiple keywords
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("Manager", "Intern"));
        assertTrue(predicate.test(new PersonBuilder().withPosition("Manager Intern").build()));

        // Only one matching keyword
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("Manager", "Director"));
        assertTrue(predicate.test(new PersonBuilder().withPosition("Manager Intern").build()));

        // Mixed-case keywords
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("mANaGer", "diREcToR"));
        assertTrue(predicate.test(new PersonBuilder().withPosition("Manager Director").build()));
    }

    @Test
    public void test_positionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PositionContainsKeywordsPredicate predicate = new PositionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPosition("Intern").build()));

        // Non-matching keyword
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("Manager"));
        assertFalse(predicate.test(new PersonBuilder().withPosition("Intern Director").build()));

        // Keywords match phone, email and address, but does not match position
        predicate = new PositionContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withPosition("Intern").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
