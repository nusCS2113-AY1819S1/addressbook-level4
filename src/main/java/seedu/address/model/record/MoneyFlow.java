package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents any form of money flow in a record in the addressbook
 * Guarantees: immutable; is valid as declared in {@link #isValidMoneyFlow(String)}
 */
public abstract class MoneyFlow {

    public static final String MESSAGE_MONEY_FLOW_CONSTRAINTS =
            "Any form of money flow should consist of '+' or '-', "
                    + "followed by a sequence of characters consisting of only digits and/or decimal points ('.')."
                    + "It must be of the following form <number>.<number>:\n"
                    + "1. <number> cannot start from '0' unless it has only 1 digit. "
                    + "There must be at least 1 digit in this field.\n"
                    + "2. At most 1 decimal point can be present. Decimal point is optional."
                    + "If decimal point is present, it must have at least 1 digit after it";

    // Any form of money flow entered must follow the format defined above
    // TODO: CHANGE THIS TO FIT THE NEW +- SCHEME
    // TODO: REFACTOR THIS TO MAKE IT LESS COMPLICATED

    private static final String MONEYFLOW_WHOLE_NUMBER_ZERO_REGEX = "0";
    private static final String MONEYFLOW_WHOLE_NUMBER_NONZERO_REGEX = "[1-9]{1}\\d*";
    private static final String MONEYFLOW_DECIMAL_PART_REGEX = ".\\d+";
    private static final String MONEYFLOW_SIGN_PART_REGEX = "[\\+-]{1}";
    // This only represents the numerical part of the string pattern
    // UNSIGNED_MONEYFLOW_VALIDATION_REGEX = "(0|[1-9]{1}\d*)($|.\d+)"
    public static final String UNSIGNED_MONEYFLOW_VALIDATION_REGEX = "(" + MONEYFLOW_WHOLE_NUMBER_ZERO_REGEX + "|"
            + MONEYFLOW_WHOLE_NUMBER_NONZERO_REGEX + ")" + "(" + "$" + "|" + MONEYFLOW_DECIMAL_PART_REGEX + ")";
    // This represents the whole pattern
    public static final String MONEYFLOW_VALIDATION_REGEX = "^" + MONEYFLOW_SIGN_PART_REGEX
            + UNSIGNED_MONEYFLOW_VALIDATION_REGEX;

    public final String value;

    public MoneyFlow(String moneyFlow) {
        requireNonNull(moneyFlow);
        checkArgument(isValidMoneyFlow(moneyFlow), MESSAGE_MONEY_FLOW_CONSTRAINTS);
        this.value = moneyFlow;
    }

    /**
     * Returns if a given string is a valid income.
     */
    public static boolean isValidMoneyFlow(String test) {
        return test.matches(MONEYFLOW_VALIDATION_REGEX);
    }

    public abstract String toString();

    public abstract boolean equals(Object other);

    public abstract int hashCode();
}
