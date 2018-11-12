package seedu.address.model.expenses;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Travel Expenses in the expenses list
 * Guarantees: immutable; is valid as declared in {@link #isValidTravelExpenses(String)}
 */
public class TravelExpenses {

    public static final String MESSAGE_TRAVEL_EXPENSES_CONSTRAINTS =
            "Travel Expenses should only contain numbers, maximum of 6 whole numbers and 2 decimal points and "
                    + "minimum 1 digit long";
    public static final String EMPLOYEE_TRAVEL_EXPENSES_VALIDATION_REGEX = "[-]?[0-9]{1,6}+([.][0-9]{1,2})?";

    public final String travelExpenses;

    /**
     * Constructs a {@code TravelExpenses}.
     *
     * @param travelExpenses A valid travel expenses.
     */
    public TravelExpenses(String travelExpenses) {
        requireNonNull(travelExpenses);
        checkArgument(isValidTravelExpenses(travelExpenses), MESSAGE_TRAVEL_EXPENSES_CONSTRAINTS);
        this.travelExpenses = travelExpenses;
    }

    /**
     * Returns true if a given string is a valid Travel Expenses.
     */
    public static boolean isValidTravelExpenses(String test) {
        return test.matches(EMPLOYEE_TRAVEL_EXPENSES_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return travelExpenses;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TravelExpenses // instanceof handles nulls
                && travelExpenses.equals(((TravelExpenses) other).travelExpenses)); // state check
    }

    @Override
    public int hashCode() {
        return travelExpenses.hashCode();
    }

}
