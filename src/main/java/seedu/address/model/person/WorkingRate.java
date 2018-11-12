package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's working rate in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWorkingRate(String)}
 */
public class WorkingRate {
    public static final String MESSAGE_WORKINGRATE_CONSTRAINTS =
        "Working rate should only contains whole or decimal numbers and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String WORKINGRATE_VALIDATION_REGEX = "\\d+(\\.\\d+)?";

    public final String value;

    /**
     * Constructs a {@code WorkingRate}.
     *
     * @param workingRate A valid working rate.
     */
    public WorkingRate(String workingRate) {
        requireNonNull(workingRate);
        checkArgument(isValidWorkingRate(workingRate), MESSAGE_WORKINGRATE_CONSTRAINTS);
        value = workingRate;
    }

    /**
     * Returns true if a given string is a valid working rate.
     */
    public static boolean isValidWorkingRate(String test) {
        return test.matches(WORKINGRATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof WorkingRate // instanceof handles nulls
            && value.equals(((WorkingRate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
