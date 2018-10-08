package seedu.address.model.group;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.GroupBuilder;

public class GroupNameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        GroupNameContainsKeywordsPredicate firstPredicate =
                new GroupNameContainsKeywordsPredicate(firstPredicateKeywordList);
        GroupNameContainsKeywordsPredicate secondPredicate =
                new GroupNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupNameContainsKeywordsPredicate firstPredicateCopy =
                new GroupNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_groupNameContainsKeywords_returnsTrue() {
        // One keyword
        GroupNameContainsKeywordsPredicate predicate =
                new GroupNameContainsKeywordsPredicate(Collections.singletonList("CS1010"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("CS1010").build()));

        // Multiple keywords
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("CS1010", "LT15"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("CS1010").withGroupLocation("LT15").build()));

        // Only one matching keyword
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("CS1010", "LT15"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("CS1010").build()));

        // Mixed-case keywords
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("cS1010", "lT15"));
        assertTrue(predicate.test(new GroupBuilder().withGroupName("CS1010").build()));
    }

    @Test
    public void test_groupNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GroupNameContainsKeywordsPredicate predicate = new GroupNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new GroupBuilder().withGroupName("CS1010").build()));

        // Non-matching keyword
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("LT15"));
        assertFalse(predicate.test(new GroupBuilder().withGroupName("CS1010").build()));

        // Keywords match groupLocation but does not match name
        predicate = new GroupNameContainsKeywordsPredicate(Arrays.asList("LT15", "MA1501"));
        assertFalse(predicate.test(new GroupBuilder().withGroupName("CS1010").withGroupLocation("LT15").build()));
    }

}
