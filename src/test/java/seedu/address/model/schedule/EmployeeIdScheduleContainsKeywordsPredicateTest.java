package seedu.address.model.schedule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.schedule.ScheduleBuilder;

public class EmployeeIdScheduleContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordsList = Collections.singletonList("000001");
        List<String> secondPredicateKeywordsList = Arrays.asList("000001", "000002");

        EmployeeIdScheduleContainsKeywordsPredicate firstPredicateList =
                new EmployeeIdScheduleContainsKeywordsPredicate(firstPredicateKeywordsList);
        EmployeeIdScheduleContainsKeywordsPredicate secondPredicateList =
                new EmployeeIdScheduleContainsKeywordsPredicate(secondPredicateKeywordsList);

        // same object -> returns true
        assertTrue(firstPredicateList.equals(firstPredicateList));

        // same values -> returns true
        EmployeeIdScheduleContainsKeywordsPredicate firstPredicateListCopy =
                new EmployeeIdScheduleContainsKeywordsPredicate(firstPredicateKeywordsList);
        assertTrue(firstPredicateList.equals(firstPredicateListCopy));

        // different types -> returns false
        assertFalse(firstPredicateList.equals(1));

        // null -> returns false
        assertFalse(firstPredicateList == null);

        // different person -> returns false
        assertFalse(firstPredicateList.equals(secondPredicateList));
    }

    @Test
    public void test_employeeIdContainsKeywords_returnsTrue() {
        // One keyword
        EmployeeIdScheduleContainsKeywordsPredicate predicate =
                new EmployeeIdScheduleContainsKeywordsPredicate(Collections.singletonList("000001"));
        assertTrue(predicate.test(new ScheduleBuilder().withEmployeeId("000001").build()));

        // Multiple keywords
        predicate = new EmployeeIdScheduleContainsKeywordsPredicate(Arrays.asList("000001", "000002"));
        assertTrue(predicate.test(new ScheduleBuilder().withEmployeeId("000002").build()));

        // Only one matching keyword
        predicate = new EmployeeIdScheduleContainsKeywordsPredicate(Arrays.asList("000002", "000003"));
        assertTrue(predicate.test(new ScheduleBuilder().withEmployeeId("000002").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmployeeIdScheduleContainsKeywordsPredicate predicate =
                new EmployeeIdScheduleContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ScheduleBuilder().withEmployeeId("000001").build()));

        // Non-matching keyword
        predicate = new EmployeeIdScheduleContainsKeywordsPredicate(Arrays.asList("000002"));
        assertFalse(predicate.test(new ScheduleBuilder().withEmployeeId("000001").build()));

        // Keywords match type and date, but does not match employee Id
        predicate = new EmployeeIdScheduleContainsKeywordsPredicate(Arrays
                .asList("20/02/2033", "LEAVE", "000002"));
        assertFalse(predicate.test(new ScheduleBuilder().withEmployeeId("000001").withType("LEAVE")
                .withDate("20/02/2033").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeyword_returnsFalse() {
        // Non-matching keyword
        EmployeeIdScheduleContainsKeywordsPredicate predicate =
                new EmployeeIdScheduleContainsKeywordsPredicate(Arrays.asList("000002"));
        assertFalse(predicate.test(new ScheduleBuilder().withEmployeeId("000001").build()));
    }

}
