package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        // List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        // AddressContainsKeywordsPredicate secondPredicate
        // = new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        // assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testAddressContainsKeywordsreturnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Bishan"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Bishan Rd").build()));

        /*
    // Multiple keywords
    predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
    assertTrue(predicate.test(new PersonBuilder().withAddress("Alice Bob").build()));

    // Only one matching keyword
    predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
    assertTrue(predicate.test(new PersonBuilder().withAddress("Alice Carol").build()));

    // Mixed-case keywords
    predicate = new AddressContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
    assertTrue(predicate.test(new PersonBuilder().withAddress("Alice Bob").build()));
        */

        //Mixed-case keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("BiShAn"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Bishan Rd").build()));
    }

    @Test
    public void testAddressDoesNotContainKeywordsreturnsFalse() {
        // Zero keywords
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withAddress("Bishan Rd").build()));

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Woodlands"));
        assertFalse(predicate.test(new PersonBuilder().withAddress("Bishan Rd").build()));

        // Keywords match name, phone and email but does not match address
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Bishan Rd").build()));
    }
}

