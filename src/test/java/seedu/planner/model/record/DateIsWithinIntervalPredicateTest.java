package seedu.planner.model.record;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import seedu.planner.testutil.RecordBuilder;

public class DateIsWithinIntervalPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateDate = Arrays.asList("31-03-1999", "31-03-2019");
        List<String> secondPredicateDate = Arrays.asList("16-4-1987", "16-3-2019");

        DateIsWithinIntervalPredicate firstPredicate =
                new DateIsWithinIntervalPredicate(
                        firstPredicateDate.get(0), firstPredicateDate.get(1));
        DateIsWithinIntervalPredicate secondPredicate =
                new DateIsWithinIntervalPredicate(
                        secondPredicateDate.get(0), secondPredicateDate.get(1));

        // same object -> returns true
        Assert.assertTrue(firstPredicate.equals(firstPredicate));

        // same values (different String arguments) -> return true
        List<String> firstPredicateDateCopy = Arrays.asList("31-3-1999", "31-3-2019");
        DateIsWithinIntervalPredicate firstPredicateCopy =
                new DateIsWithinIntervalPredicate(
                        firstPredicateDateCopy.get(0), firstPredicateDateCopy.get(1));
        Assert.assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> return false
        Assert.assertFalse(firstPredicate.equals(1));

        // null -> return false
        Assert.assertFalse(firstPredicate.equals(null));

        // different predicate -> return false
        Assert.assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateContainsDifferentArgumentExpressionButSameValue_returnsTrue() {
        List<String> firstPredicateDate = Arrays.asList("31-03-1999", "30-04-2019");
        DateIsWithinIntervalPredicate firstPredicate =
                new DateIsWithinIntervalPredicate(
                        firstPredicateDate.get(0), firstPredicateDate.get(1));
        // The Start Day
        Assert.assertTrue(firstPredicate.test(new RecordBuilder().withDate("31-3-1999").build()));

        // The Day within the time range
        Assert.assertTrue(firstPredicate.test(new RecordBuilder().withDate("15-4-1999").build()));

        // The End Date
        Assert.assertTrue(firstPredicate.test(new RecordBuilder().withDate("30-4-2019").build()));
    }

    @Test
    public void test_dateContainsDifferentArgumentExpressionButSameValue_returnsFalse() {
        // Both Start Date and End Date are okay but the date is not within the interval.
        DateIsWithinIntervalPredicate firstPredicate = new DateIsWithinIntervalPredicate("31-03-1999", "30-04-2019");
        Assert.assertFalse(firstPredicate.test(new RecordBuilder().withDate("31-3-1998").build()));
    }
}
