package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class EmployeeIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordsList = Collections.singletonList("000001");
        List<String> secondPredicateKeywordsList = Arrays.asList("000001", "000002");
        String firstPredicateKeyword = "000001";
        String secondPredicateKeyword = "000002";

        EmployeeIdContainsKeywordsPredicate firstPredicateList =
                new EmployeeIdContainsKeywordsPredicate(firstPredicateKeywordsList);
        EmployeeIdContainsKeywordsPredicate secondPredicateList =
                new EmployeeIdContainsKeywordsPredicate(secondPredicateKeywordsList);
        EmployeeIdContainsKeywordsPredicate firstPredicate =
                new EmployeeIdContainsKeywordsPredicate(firstPredicateKeyword);
        EmployeeIdContainsKeywordsPredicate secondPredicate =
                new EmployeeIdContainsKeywordsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicateList.equals(firstPredicateList));
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmployeeIdContainsKeywordsPredicate firstPredicateListCopy =
                new EmployeeIdContainsKeywordsPredicate(firstPredicateKeywordsList);
        assertTrue(firstPredicateList.equals(firstPredicateListCopy));
        EmployeeIdContainsKeywordsPredicate firstPredicateCopy =
                new EmployeeIdContainsKeywordsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicateList.equals(1));
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicateList == null);
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicateList.equals(secondPredicateList));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_employeeIdContainsKeywords_returnsTrue() {
        // One keyword
        EmployeeIdContainsKeywordsPredicate predicate =
                new EmployeeIdContainsKeywordsPredicate(Collections.singletonList("000001"));
        assertTrue(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Multiple keywords
        predicate = new EmployeeIdContainsKeywordsPredicate(Arrays.asList("000001", "000002"));
        assertTrue(predicate.test(new PersonBuilder().withEmployeeId("000002").build()));

        // Only one matching keyword
        predicate = new EmployeeIdContainsKeywordsPredicate(Arrays.asList("000002", "000003"));
        assertTrue(predicate.test(new PersonBuilder().withEmployeeId("000002").build()));
    }

    @Test
    public void test_employeeIdContainsKeyword_returnsTrue() {
        // One keyword
        EmployeeIdContainsKeywordsPredicate predicate =
                new EmployeeIdContainsKeywordsPredicate("000001");
        assertTrue(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmployeeIdContainsKeywordsPredicate predicate =
                new EmployeeIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Non-matching keyword
        predicate = new EmployeeIdContainsKeywordsPredicate(Arrays.asList("000002"));
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Keywords match phone, email and address, but does not match employee Id
        predicate = new EmployeeIdContainsKeywordsPredicate(Arrays
                .asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void test_employeeIdDoesNotContainKeyword_returnsFalse() {
        // Zero keyword
        EmployeeIdContainsKeywordsPredicate predicate =
                new EmployeeIdContainsKeywordsPredicate("");
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));

        // Non-matching keyword
        predicate = new EmployeeIdContainsKeywordsPredicate("000002");
        assertFalse(predicate.test(new PersonBuilder().withEmployeeId("000001").build()));
    }
}
