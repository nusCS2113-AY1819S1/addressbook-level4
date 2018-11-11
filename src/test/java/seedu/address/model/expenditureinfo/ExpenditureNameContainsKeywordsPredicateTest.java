package seedu.address.model.expenditureinfo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ExpenditureBuilder;

public class ExpenditureNameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        ExpenditureNameContainsKeywordsPredicate firstPredicate =
                new ExpenditureNameContainsKeywordsPredicate(firstPredicateKeywordList);
        ExpenditureNameContainsKeywordsPredicate secondPredicate =
                new ExpenditureNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ExpenditureNameContainsKeywordsPredicate firstPredicateCopy =
                new ExpenditureNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different expenditure -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ExpenditureNameContainsKeywordsPredicate predicate =
                new ExpenditureNameContainsKeywordsPredicate(Collections.singletonList("Chicken"));
        assertTrue(predicate.test(new ExpenditureBuilder().withDescription("Chicken rice").build()));

        // Multiple keywords
        predicate = new ExpenditureNameContainsKeywordsPredicate(Arrays.asList("Chicken", "rice"));
        assertTrue(predicate.test(new ExpenditureBuilder().withDescription("Chicken rice").build()));

        // Only one matching keyword
        predicate = new ExpenditureNameContainsKeywordsPredicate(Arrays.asList("Chicken", "set"));
        assertTrue(predicate.test(new ExpenditureBuilder().withDescription("Chicken rice").build()));

        // Mixed-case keywords
        predicate = new ExpenditureNameContainsKeywordsPredicate(Arrays.asList("chicken", "Rice"));
        assertTrue(predicate.test(new ExpenditureBuilder().withDescription("Chicken rice").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ExpenditureNameContainsKeywordsPredicate predicate =
                new ExpenditureNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ExpenditureBuilder().withDescription("Chicken rice").build()));

        // Non-matching keyword
        predicate = new ExpenditureNameContainsKeywordsPredicate(Arrays.asList("Cola"));
        assertFalse(predicate.test(new ExpenditureBuilder().withDescription("Chicken rice").build()));

        // Keywords match category, date, money, but does not match description
        predicate = new ExpenditureNameContainsKeywordsPredicate(Arrays.asList("Food", "01-01", "-2018", "12"));
        assertFalse(predicate.test(new ExpenditureBuilder().withDescription("Chicken rice").withCategory("Food")
                .withDate("01-01-2018").withMoney("12").build()));
    }
}
