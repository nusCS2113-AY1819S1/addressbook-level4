package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

//@@author chokxy
public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("Alice@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@email.com").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice@email.com", "Bob@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@email.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Bob@email.com").build()));

        // Only one matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Bob@email.com", "Carol@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Carol@email.com").build()));

        // Mixed-case keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("aLIce@email.com", "bOB@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@email.com").build()));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Bob@email.com").build()));

        //Mixed-case keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("aLIce@email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("Alice@email.com").build()));
    }


    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("carol@email.com"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));

        // Keywords match name, phone and Email, but does not match email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
