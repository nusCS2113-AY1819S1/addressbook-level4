package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author pinjuen

/**
 * Represents a Person checked in/out to work in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMode(String)}
 */
public class Mode {

    public static final String MESSAGE_MODE_CONSTRAINTS =
            "Mode should only contains \"in\" or \"out\" and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MODE_VALIDATION_REGEX_IN = "in";
    public static final String MODE_VALIDATION_REGEX_OUT = "out";

    public final String value;

    /**
     * Constructs a {@code Mode}.
     *
     * @param mode A valid mode.
     */
    public Mode(String mode) {
        requireNonNull(mode);
        checkArgument(isValidMode(mode), MESSAGE_MODE_CONSTRAINTS);
        value = mode;
    }

    /**
     * Returns true if a given string is a valid mode.
     */
    public static boolean isValidMode(String test) {
        return test.matches(MODE_VALIDATION_REGEX_IN) || test.matches(MODE_VALIDATION_REGEX_OUT);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mode // instanceof handles nulls
                && value.equals(((Mode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
