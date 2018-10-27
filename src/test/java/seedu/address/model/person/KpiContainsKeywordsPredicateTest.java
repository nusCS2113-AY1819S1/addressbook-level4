//@@author lws803
package seedu.address.model.person;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class KpiContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("4.0");
        List<String> secondPredicateKeywordList = Arrays.asList("4.0", "4.1");

        KpiContainsKeywordPredicate firstPredicate = new KpiContainsKeywordPredicate(firstPredicateKeywordList);
        KpiContainsKeywordPredicate secondPredicate = new KpiContainsKeywordPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        KpiContainsKeywordPredicate firstPredicateCopy = new KpiContainsKeywordPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

}
