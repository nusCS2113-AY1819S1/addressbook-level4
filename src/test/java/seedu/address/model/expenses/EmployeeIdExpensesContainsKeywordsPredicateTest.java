package seedu.address.model.expenses;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.expenses.ExpensesBuilder;

public class EmployeeIdExpensesContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordsList = Collections.singletonList("000001");
        List<String> secondPredicateKeywordsList = Arrays.asList("000001", "000002");

        EmployeeIdExpensesContainsKeywordsPredicate firstPredicateList =
                new EmployeeIdExpensesContainsKeywordsPredicate(firstPredicateKeywordsList);
        EmployeeIdExpensesContainsKeywordsPredicate secondPredicateList =
                new EmployeeIdExpensesContainsKeywordsPredicate(secondPredicateKeywordsList);

        // same object -> returns true
        assertTrue(firstPredicateList.equals(firstPredicateList));

        // same values -> returns true
        EmployeeIdExpensesContainsKeywordsPredicate firstPredicateListCopy =
                new EmployeeIdExpensesContainsKeywordsPredicate(firstPredicateKeywordsList);
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
        EmployeeIdExpensesContainsKeywordsPredicate predicate =
                new EmployeeIdExpensesContainsKeywordsPredicate(Collections.singletonList("000001"));
        assertTrue(predicate.test(new ExpensesBuilder().withEmployeeId("000001").build()));

        // Multiple keywords
        predicate = new EmployeeIdExpensesContainsKeywordsPredicate(Arrays.asList("000001", "000002"));
        assertTrue(predicate.test(new ExpensesBuilder().withEmployeeId("000002").build()));

        // Only one matching keyword
        predicate = new EmployeeIdExpensesContainsKeywordsPredicate(Arrays.asList("000002", "000003"));
        assertTrue(predicate.test(new ExpensesBuilder().withEmployeeId("000002").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmployeeIdExpensesContainsKeywordsPredicate predicate =
                new EmployeeIdExpensesContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ExpensesBuilder().withEmployeeId("000001").build()));

        // Non-matching keyword
        predicate = new EmployeeIdExpensesContainsKeywordsPredicate(Arrays.asList("000002"));
        assertFalse(predicate.test(new ExpensesBuilder().withEmployeeId("000001").build()));

        // Keywords match expensesAmount, travelExpenses, medicalExpenses, miscellaneousExpenses,
        // but does not match employee Id
        predicate = new EmployeeIdExpensesContainsKeywordsPredicate(Arrays
                .asList("369", "123", "123", "123", "000002"));
        assertFalse(predicate.test(new ExpensesBuilder().withEmployeeId("000001").withExpensesAmount("123",
                "123", "123").withTravelExpenses("123")
                .withMedicalExpenses("123").withMiscellaneousExpenses("123").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeyword_returnsFalse() {
        // Non-matching keyword
        EmployeeIdExpensesContainsKeywordsPredicate predicate =
                new EmployeeIdExpensesContainsKeywordsPredicate(Arrays.asList("000002"));
        assertFalse(predicate.test(new ExpensesBuilder().withEmployeeId("000001").build()));
    }

}
