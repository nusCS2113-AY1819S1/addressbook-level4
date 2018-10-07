package seedu.planner.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents any form of money flow in a record in the financialplanner
 * Guarantees: immutable; is valid as declared in {@link #isValidMoneyFlow(String)}
 */
public class MoneyFlow {
    //TODO: Refactor this
    public static final String MESSAGE_MONEY_FLOW_CONSTRAINTS =
            "Any form of money flow should consist of '+' or '-', "
                    + "followed by a sequence of characters consisting of only digits and/or decimal points ('.')."
                    + "It must be of the following form <number>.<number>:\n"
                    + "1. <number> cannot start from '0' unless it has only 1 digit. "
                    + "There must be at least 1 digit in this field.\n"
                    + "2. At most 1 decimal point can be present. Decimal point is optional."
                    + "If decimal point is present, it must have at least 1 digit after it";

    public static final String SIGN_REGEX = ("(?<sign>[-+])");
    public static final String MONEYFLOW_NO_SIGN_REGEX = ("(?<money>.*)");
    public static final String CURRENCY = "$";

    // Any form of money flow entered must follow the format defined above
    private static final String MONEYFLOW_WHOLE_NUMBER_ZERO_REGEX = "0";
    private static final String MONEYFLOW_WHOLE_NUMBER_NONZERO_REGEX = "[1-9]{1}\\d*";
    private static final String MONEYFLOW_DECIMAL_PART_REGEX = ".\\d+";
    private static final String MONEYFLOW_SIGN_PART_REGEX = "[\\+-]";
    // This only represents the numerical part of the string pattern
    // UNSIGNED_MONEYFLOW_VALIDATION_REGEX = "(0|[1-9]{1}\d*)($|.\d+)"
    private static final String UNSIGNED_MONEYFLOW_VALIDATION_REGEX = "(" + MONEYFLOW_WHOLE_NUMBER_ZERO_REGEX + "|"
            + MONEYFLOW_WHOLE_NUMBER_NONZERO_REGEX + ")" + "(" + "$" + "|" + MONEYFLOW_DECIMAL_PART_REGEX + ")";
    // This represents the whole pattern
    private static final String MONEYFLOW_VALIDATION_REGEX = "^" + MONEYFLOW_SIGN_PART_REGEX
            + UNSIGNED_MONEYFLOW_VALIDATION_REGEX;

    private static final Pattern MONEY_PATTERN = Pattern.compile(SIGN_REGEX + MONEYFLOW_NO_SIGN_REGEX);

    public final String value;
    public final double valueDouble;

    public MoneyFlow(String moneyFlow) {
        requireNonNull(moneyFlow);
        checkArgument(isValidMoneyFlow(moneyFlow), MESSAGE_MONEY_FLOW_CONSTRAINTS);
        this.value = moneyFlow;
        valueDouble = Double.valueOf(moneyFlow);
    }

    /**
     * Returns if a given string is a valid moneyflow parameter.
     */
    public static boolean isValidMoneyFlow(String test) {
        return test.matches(MONEYFLOW_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        Matcher matcher = MONEY_PATTERN.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalStateException();
        }
        return matcher.group("sign") + CURRENCY + matcher.group("money");
    }

    public double toDouble () { return valueDouble; }

    public boolean isSmaller (double moneyin) { return (moneyin> valueDouble); }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MoneyFlow // instanceof handles nulls
                && value.equals(((MoneyFlow) other).value)); // state check
    }

    public int hashCode() {
        return value.hashCode();
    }
}
