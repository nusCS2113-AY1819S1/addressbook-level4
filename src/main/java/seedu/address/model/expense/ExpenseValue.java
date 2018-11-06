package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an expense's value in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpenseValue(String)}
 */
public class ExpenseValue {
    public static final String MESSAGE_EXPENSE_VALUE_CONSTRAINTS =
            "Expense value should only contain numbers, in 2 decimal points and between 0.01 to 99999.99";

    public static final int MAX_EXPENSE_VALUE_DIGIT = 5;
    public static final double ZERO_EXPENSE_VALUE = 0.00;
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
        this.expenseValue = eliminateLeadingZero(expenseValue);
    }

    /**
     * Returns true if a given string is a valid value with 2 decimal places.
     */
    public static boolean isValidExpenseValue(String test) {
        if (test.matches(EXPENSE_VALUE_VALIDATION_REGEX)) {
            test = eliminateLeadingZero(test);
            if (test.length() > (MAX_EXPENSE_VALUE_DIGIT + 3)) {
                return false;
            } else {
                return Double.parseDouble(test) != ZERO_EXPENSE_VALUE;
            }
        }
        return false;
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

    /**
     * Returns a String of expenseValue without unnecessary leading zeros
     */
    private static String eliminateLeadingZero(String expenseValue) {
        for (int i = 0; i < expenseValue.length(); i++) {
            if (expenseValue.charAt(i) != '0') {
                if (expenseValue.charAt(i) == '.') {
                    return expenseValue.substring(i - 1);
                } else {
                    return expenseValue.substring(i);
                }
            }
        }
        return expenseValue;
    }
}

