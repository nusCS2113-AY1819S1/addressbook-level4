package model.drink;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import model.testutil.DrinkBuilder;
import seedu.address.model.drink.NameContainsKeywordsPredicate;

public class NameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different drink -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("Coke"));
        assertTrue(predicate.test(new DrinkBuilder().withName("Coke Zero").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Coke", "Zero"));
        assertTrue(predicate.test(new DrinkBuilder().withName("Coke Zero").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Coke", "Lemon"));
        assertTrue(predicate.test(new DrinkBuilder().withName("Coke Zero").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("cOkE", "lemON"));
        assertTrue(predicate.test(new DrinkBuilder().withName("Coke Lemon").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new DrinkBuilder().withName("Coke").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Pepsi"));
        assertFalse(predicate.test(new DrinkBuilder().withName("Coke").build()));

        // Keywords match retail, cost prices, quantity, tags, but different name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("1.20", "5.40", "50", "Pepsi", "softDrink"));
        assertFalse(predicate.test(new DrinkBuilder().withName("Coke").withCostPrice("1.20")
                .withRetailPrice("5.40").withQuantity("50").withTags("softDrink").build()));
    }
}
