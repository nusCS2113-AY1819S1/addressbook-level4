package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Record's income in the financial planner.
 * Guarantees: immutable; is valid as declared in {@link #isValidIncome(String)}
 */
public class Income extends MoneyFlow {

    public static final String MESSAGE_INCOME_CONSTRAINTS =
            "Any form of income should consist of '+', "
                    + "followed by a sequence of characters consisting of only digits and/or decimal points ('.')."
                    + "It must be of the following form <number>.<number>:\n"
                    + "1. <number> cannot start from '0' unless it has only 1 digit. "
                    + "There must be at least 1 digit in this field.\n"
                    + "2. At most 1 decimal point can be present. Decimal point is optional."
                    + "If decimal point is present, it must have at least 1 digit after it";

    private static final String INCOME_SIGN_REGEX = "[\\+]";
    private static final String INCOME_VALIDATION_REGEX = "^" + INCOME_SIGN_REGEX
            + "(" + UNSIGNED_MONEYFLOW_VALIDATION_REGEX + ")";

    /**
     * Constructs an {@code Income}.
     *
     * @param income A valid income.
     */
    public Income(String income) {
        super(income);
        requireNonNull(income);
        checkArgument(isValidIncome(income), MESSAGE_INCOME_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid income.
     */
    public static boolean isValidIncome(String test) {
        return test.matches(INCOME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Income // instanceof handles nulls
                && value.equals(((Income) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
