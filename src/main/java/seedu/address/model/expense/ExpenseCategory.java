package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Expense's category in the expense book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpenseCategory(String)}
 */
public class ExpenseCategory {
    public static final String MESSAGE_EXPENSE_CATEGORY_CONSTRAINTS =
            "Expense category should only contain alphabets.";

    public static final String EXPENSE_CATEGORY_VALIDATION_REGEX = "\\p{Alpha}+";

    public final String expenseCategory;

    /**
     * Constructs a {@code ExpenseCategory}.
     *
     * @param expenseCategory A valid expense category.
     */
    public ExpenseCategory(String expenseCategory) {
        requireNonNull(expenseCategory);
        checkArgument(isValidExpenseCategory(expenseCategory), MESSAGE_EXPENSE_CATEGORY_CONSTRAINTS);
        this.expenseCategory = expenseCategory.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid category.
     */
    public static boolean isValidExpenseCategory(String test) {
        return test.matches(EXPENSE_CATEGORY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return expenseCategory;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseCategory // instanceof handles nulls
                && expenseCategory.equals(((ExpenseCategory) other).expenseCategory)); // state check
    }

    @Override
    public int hashCode() {
        return expenseCategory.hashCode();
    }
}
