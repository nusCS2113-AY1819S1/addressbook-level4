package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class DepartmentContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Admin");
        List<String> secondPredicateKeywordList = Arrays.asList("Admin", "Finance");

        DepartmentContainsKeywordsPredicate firstPredicate = new DepartmentContainsKeywordsPredicate(
            firstPredicateKeywordList);
        DepartmentContainsKeywordsPredicate secondPredicate = new DepartmentContainsKeywordsPredicate(
            secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DepartmentContainsKeywordsPredicate firstPredicateCopy = new DepartmentContainsKeywordsPredicate(
            firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_departmentContainsKeywords_returnsTrue() {
        // One keyword
        DepartmentContainsKeywordsPredicate predicate = new DepartmentContainsKeywordsPredicate(
            Collections.singletonList("Marketing"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("Marketing").build()));

    }

    @Test
    public void test_departmentDoesNotContainKeywords_returnsFalse() {

        // Non-matching keyword
        DepartmentContainsKeywordsPredicate secondPredicate = new DepartmentContainsKeywordsPredicate(
            Arrays.asList("Finance"));
        assertFalse(secondPredicate.test(new PersonBuilder().withDepartment("Marketing").build()));

        // Keywords match name, phone, email and address, but does not match department
        DepartmentContainsKeywordsPredicate thirdPredicate = new DepartmentContainsKeywordsPredicate(
            Arrays.asList("12345", "Alice", "alice@email.com", "Main", "Street", "Admin"));
        assertFalse(thirdPredicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withDepartment("Finance").build()));
    }
}
