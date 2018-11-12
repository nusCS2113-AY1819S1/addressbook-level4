package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Bonus in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidBonus(String)}
 */
public class Bonus {
    public static final String MESSAGE_BONUS_CONSTRAINTS =
            "Bonus should only contain positive numbers and maximum of 2 decimal places from 0 to 24,"
                    + " and it should not be blank";
    public static final String BONUS_VALIDATION_REGEX = "(([0-9]{1,7}([.][0-9]{1,2})?)|(1[0-9]{7}([.][0-9]{1,2})?)"
            + "|(2[0-3]([0-9]{1,6})([.][0-9]{1,2})?))";
    public final String value;

    /**
     * Constructs a {@code bonus}.
     *
     * @param bonus A valid bonus.
     */

    public Bonus(String bonus) {
        requireNonNull(bonus);
        checkArgument(isValidBonus(bonus), MESSAGE_BONUS_CONSTRAINTS);
        value = bonus;
    }

    /**
     * Returns true if a given string is a valid bonus.
     */
    public static boolean isValidBonus(String test) {
        return test.matches(BONUS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Bonus // instanceof handles nulls
                && value.equals(((Bonus) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
