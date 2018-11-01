package seedu.address.model.clubbudget;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the Total Budget made available by the NUSSU Exco in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidTotalBudget(String)}
 */

public class TotalBudget {

    public static final String MESSAGE_TOTAL_BUDGET_CONSTRAINTS = "Total budget should only contain numbers "
            + "including zero. It should be given in SGD";
    public static final String TOTAL_BUDGET_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code TotalBudget}.
     * @param totalBudget A valid total budget.
     */
    public TotalBudget(String totalBudget) {
        requireNonNull(totalBudget);
        checkArgument(isValidTotalBudget(totalBudget), MESSAGE_TOTAL_BUDGET_CONSTRAINTS);
        value = totalBudget;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidTotalBudget(String test) {
        return test.matches(TOTAL_BUDGET_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TotalBudget // instanceof handles nulls
                && value.equals(((TotalBudget) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
