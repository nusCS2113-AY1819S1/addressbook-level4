package seedu.address.model.statistic;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Inventory in the month's Statistic
 */
public class Expense {
    public static final String MESSAGE_EXPENSE_CONSTRAINTS =
            "Expense should be numerical and in 2 decimal places or none at all\n"
                    + "E.g. 4, 3.02";
    private static final String QUANTITY_VALIDATION_REGEX = "\\d+(\\.\\d{2})?";
    private volatile String value;

    /**
     * Constructor for Json Jackson
     */
    public Expense() {
        super();
    }

    public Expense(String expense) {
        requireNonNull(expense);
        checkArgument(isValidExpense(expense), MESSAGE_EXPENSE_CONSTRAINTS);
        value = expense;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static boolean isValidExpense(String test) {
        return test.matches(QUANTITY_VALIDATION_REGEX);
    }

    /**
     * Increase expense
     * @param price
     * @param quantity
     */
    public void increase(String price, String quantity) {
        this.value = Float.toString(
                Float.parseFloat(value) + (Float.parseFloat(price) * Float.parseFloat(quantity)));
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Expense // instanceof handles nulls
                && value.equals(((Expense) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
