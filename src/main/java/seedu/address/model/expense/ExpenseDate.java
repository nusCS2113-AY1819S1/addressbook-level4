package seedu.address.model.expense;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Represents an expense's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class ExpenseDate {
    public static final String MESSAGE_EXPENSE_DATE_CONSTRAINTS =
            "Expense date should be in DD/MM/YYYY format.";

    public final String expenseDate;

    /**
     * Constructs a {@code ExpenseDate}.
     *
     * @param expenseDate A valid expense date.
     */
    public ExpenseDate(String expenseDate) {
        requireNonNull(expenseDate);
        checkArgument(isValidDate(expenseDate), MESSAGE_EXPENSE_DATE_CONSTRAINTS);
        this.expenseDate = expenseDate;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        String pattern = "dd/MM/yyyy";
        if (test.length() != pattern.length()) {
            return false;
        }
        DateFormat dateFormat = new SimpleDateFormat (pattern);
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(test);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    @Override
    public String toString() {
        return expenseDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseDate // instanceof handles nulls
                && expenseDate.equals(((ExpenseDate) other).expenseDate)); // state check
    }

    @Override
    public int hashCode() {
        return expenseDate.hashCode();
    }
}

