package seedu.address.model.event;
/*
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.testutil.EventBuilder;

public class EventContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        Map<Prefix, List<String> > firstPredicateKeywordMap = new HashMap<>();
        Map<Prefix, List<String> > secondPredicateKeywordMap = new HashMap<>();

        List<String> firstPredicateKeywordPrefixList = Collections.singletonList("first");
        List<String> secondPredicateKeywordPrefixList = Collections.singletonList("first");
        List<String> secondPredicateNamePrefixList = Collections.singletonList("second");


        firstPredicateKeywordMap.put(PREFIX_KEYWORD, firstPredicateKeywordPrefixList);
        secondPredicateKeywordMap.put(PREFIX_KEYWORD, secondPredicateKeywordPrefixList);
        secondPredicateKeywordMap.put(PREFIX_NAME, secondPredicateNamePrefixList);

        EventContainsKeywordsPredicate firstPredicate =
                new EventContainsKeywordsPredicate(firstPredicateKeywordMap);
        EventContainsKeywordsPredicate secondPredicate =
                new EventContainsKeywordsPredicate(secondPredicateKeywordMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventContainsKeywordsPredicate firstPredicateCopy =
                new EventContainsKeywordsPredicate(firstPredicateKeywordMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EventContainsKeywordsPredicate predicate =
                new EventContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice Bob").build()));

        // Keywords match phone and email, but does not match name, venue and dateTime
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com"));
        assertTrue(predicate.test(new EventBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withVenue("Main Street").withDateTime("10/10/2010 10:10").build()));
    }

    @Test
    public void test_eventDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventContainsKeywordsPredicate predicate = new EventContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new EventContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new EventBuilder().withName("Alice Bob").build()));
    }

    public void setup_predicate(List<String> keywords) {

    }
}
*/