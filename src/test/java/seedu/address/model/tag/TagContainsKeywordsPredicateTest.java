package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.testutil.PersonBuilder;

//@@author chokxy
public class TagContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagContainsKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("Friend"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Friend", "Colleague"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague").build()));


        // Only one matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Dover"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("fRiEnD", "cOllEagUe"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague").build()));

        //Mixed-case keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("FRIENd"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Friend").build()));
    }

    @Test
    public void test_tagDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Friend").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("fri"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Friend").build()));

        // Keywords match name, phone and email but does not match address
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Bishan"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Bishan").build()));
    }
}
