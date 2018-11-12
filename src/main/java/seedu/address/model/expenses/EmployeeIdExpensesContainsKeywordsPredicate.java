package seedu.address.model.expenses;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Expenses}'s {@code Employee_Id} matches any of the Id given.
 */


public class EmployeeIdExpensesContainsKeywordsPredicate implements Predicate<Expenses> {
    private final List<String> keywords;

    public EmployeeIdExpensesContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Expenses expenses) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(expenses.getEmployeeId().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployeeIdExpensesContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EmployeeIdExpensesContainsKeywordsPredicate) other).keywords)); // state check
    }
}
