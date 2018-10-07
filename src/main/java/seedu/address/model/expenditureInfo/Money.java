package seedu.address.model.expenditureinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 *
 */
public class Money {
    public static final String MESSAGE_MONEY_CONSTRAINTS =
            "Dates should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String MONEY_VALIDATION_REGEX = "[\\p{Punct}][\\d{1,}]";

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
                || (other instanceof Date // instanceof handles nulls
                && addingMoney.equals(((Money) other).addingMoney)); // state check
    }

    @Override
    public int hashCode() {
        return addingMoney.hashCode();
    }
}
