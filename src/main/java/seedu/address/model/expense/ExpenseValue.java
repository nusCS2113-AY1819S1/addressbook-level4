package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an expense's value in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpenseValue(String)}
 */
public class ExpenseValue {
    public static final String MESSAGE_EXPENSE_VALUE_CONSTRAINTS =
            "Expense value should only contain numbers, and it should 2 decimal points.";

    public static final String EXPENSE_VALUE_VALIDATION_REGEX = "\\d+\\.\\d{2}";
    public final String expenseValue;

    /**
     * Constructs a {@code ExpenseValue}.
     *
     * @param expenseValue A valid expense value.
     */
    public ExpenseValue(String expenseValue) {
        requireNonNull(expenseValue);
        checkArgument(isValidExpenseValue(expenseValue), MESSAGE_EXPENSE_VALUE_CONSTRAINTS);
        this.expenseValue = expenseValue;
    }

    /**
     * Returns true if a given string is a valid value with 2 decimal places.
     */
    public static boolean isValidExpenseValue(String test) {
        return test.matches(EXPENSE_VALUE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return expenseValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseValue // instanceof handles nulls
                && expenseValue.equals(((ExpenseValue) other).expenseValue)); // state check
    }

    @Override
    public int hashCode() {
        return expenseValue.hashCode();
    }
}

