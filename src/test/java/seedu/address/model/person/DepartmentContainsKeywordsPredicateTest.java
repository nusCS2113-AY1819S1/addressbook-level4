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
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        DepartmentContainsKeywordsPredicate firstPredicate =
                new DepartmentContainsKeywordsPredicate(firstPredicateKeywordList);
        DepartmentContainsKeywordsPredicate secondPredicate =
                new DepartmentContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DepartmentContainsKeywordsPredicate firstPredicateCopy =
               new DepartmentContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_departmentContainsKeyword_returnsTrue() {
        // One keyword
        DepartmentContainsKeywordsPredicate predicate =
                new DepartmentContainsKeywordsPredicate(Collections.singletonList("Finance"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("Finance").build()));

        // Multiple keywords
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("Finance", "IT"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("Finance IT").build()));

        // Only one matching keyword
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("Finance", "IT"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("IT HR").build()));

        // Mixed-case keywords
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("fINance", "iT"));
        assertTrue(predicate.test(new PersonBuilder().withDepartment("Finance IT").build()));
    }

    @Test
    public void test_departmentDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        DepartmentContainsKeywordsPredicate predicate =
                new DepartmentContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withDepartment("Finance").build()));

        // Non-matching keyword
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("HR"));
        assertFalse(predicate.test(new PersonBuilder().withDepartment("Finance IT").build()));

        // Keywords match phone, email and address, but does not match department
        predicate = new DepartmentContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
                "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withDepartment("HR").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
