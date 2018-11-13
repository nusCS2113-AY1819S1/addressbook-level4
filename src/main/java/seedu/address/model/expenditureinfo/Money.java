//@@author SHININGGGG
package seedu.address.model.expenditureinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 *  Represents a Expenditure's category in the expenditure tracker.
 *  Guarantees: immutable; is valid as declared in {@link #isValidMoney(String)}
 */
public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS =
            "Moneys should only contain positive integer or floating point number.";
    public static final String MONEY_VALIDATION_REGEX = "[0-9]+\\.?[0-9]*";

    public final String addingMoney;

    public Money(String money) {
        requireNonNull(money);
        checkArgument(isValidMoney(money), MESSAGE_MONEY_CONSTRAINTS);
        addingMoney = money;
    }

    public static boolean isValidMoney(String test) {
        return test.matches(MONEY_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return addingMoney;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Money // instanceof handles nulls
                && addingMoney.equals(((Money) other).addingMoney)); // state check
    }

    @Override
    public int hashCode() {
        return addingMoney.hashCode();
    }
}
