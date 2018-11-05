package seedu.address.model.budgetelements;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the expected number of attendees for every event a club is planning to have, in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpectedTurnout(String)}
 */

public class ExpectedTurnout {

    public static final String MESSAGE_EXPECTED_TURNOUT_CONSTRAINTS =
            "Expected turnout should only contain positive numbers, and it can be any number including zero";
    public static final String EXPECTED_TURNOUT_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code ExpectedTurnout}.
     *
     * @param expectedTurnout A valid phone number.
     */
    public ExpectedTurnout(String expectedTurnout) {
        requireNonNull(expectedTurnout);
        checkArgument(isValidExpectedTurnout(expectedTurnout), MESSAGE_EXPECTED_TURNOUT_CONSTRAINTS);
        value = expectedTurnout;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidExpectedTurnout(String test) {
        return test.matches(EXPECTED_TURNOUT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpectedTurnout // instanceof handles nulls
                && value.equals(((ExpectedTurnout) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
