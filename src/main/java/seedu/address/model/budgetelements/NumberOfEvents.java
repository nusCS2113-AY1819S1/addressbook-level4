package seedu.address.model.budgetelements;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of events a club is planning to have, in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNumberOfEvents(String)}
 */

public class NumberOfEvents {

    public static final String MESSAGE_NUMBER_OF_EVENTS_CONSTRAINTS =
            "Number of events should only contain positive numbers, and it can be any number including zero";
    public static final String NUMBER_OF_EVENTS_VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code NumberOfEvents}.
     *
     * @param numberOfEvents A valid phone number.
     */
    public NumberOfEvents(String numberOfEvents) {
        requireNonNull(numberOfEvents);
        checkArgument(isValidNumberOfEvents(numberOfEvents), MESSAGE_NUMBER_OF_EVENTS_CONSTRAINTS);
        value = numberOfEvents;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidNumberOfEvents(String test) {
        return test.matches(NUMBER_OF_EVENTS_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NumberOfEvents // instanceof handles nulls
                && value.equals(((NumberOfEvents) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
