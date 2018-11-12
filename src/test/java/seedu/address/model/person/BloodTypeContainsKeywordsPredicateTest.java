package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author chokxy
public class BloodTypeContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        BloodTypeContainsKeywordsPredicate firstPredicate =
                new BloodTypeContainsKeywordsPredicate(firstPredicateKeywordList);
        BloodTypeContainsKeywordsPredicate secondPredicate =
                new BloodTypeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        BloodTypeContainsKeywordsPredicate firstPredicateCopy =
                new BloodTypeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_bloodTypeContainsKeywords_returnsTrue() {

        // One keyword
        BloodTypeContainsKeywordsPredicate predicate =
                new BloodTypeContainsKeywordsPredicate(Collections.singletonList("O+"));
        assertTrue(predicate.test(new PersonBuilder().withBloodType("O+").build()));

        // Multiple keywords
        predicate = new BloodTypeContainsKeywordsPredicate(Arrays.asList("O+", "A-"));
        assertTrue(predicate.test(new PersonBuilder().withBloodType("O+").build()));
        assertTrue(predicate.test(new PersonBuilder().withBloodType("A-").build()));

        // Only one matching keyword
        predicate = new BloodTypeContainsKeywordsPredicate(Arrays.asList("O-", "A+"));
        assertTrue(predicate.test(new PersonBuilder().withBloodType("A+").build()));
    }

    @Test
    public void test_bloodTypeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        BloodTypeContainsKeywordsPredicate predicate =
                new BloodTypeContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withBloodType("O+").build()));

        // Non-matching keyword
        predicate = new BloodTypeContainsKeywordsPredicate(Arrays.asList("O+"));
        assertFalse(predicate.test(new PersonBuilder().withBloodType("A-").build()));

        // Keywords match name, phone and email but does not match address
        predicate = new BloodTypeContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withBloodType("O+").build()));
    }
}
