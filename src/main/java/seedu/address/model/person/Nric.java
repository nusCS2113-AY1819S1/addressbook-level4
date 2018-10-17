package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a person's NRIC in Health Book.
 */
public class Nric {
    public static final String NRIC_VALIDATION_REGEX = "[ST]" + "[\\d]{7}" + "[A-Z]";
    public static final String MESSAGE_NRIC_CONSTRAINTS = "NRICs should adhere to the following constraints:\n"
            + "1. It should begin with either S or T;\n"
            + "2. The next 7 characters should be digits between 0 - 9, and;\n"
            + "3. It should end of with any alphabet.";

    public final String value;

    /**
     * Constructs an {@code Nric}.
     *
     * @param nric A valid nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        value = nric;
    }

    /**
     * Returns if a given string is a valid NRIC.
     */
    public static boolean isValidNric(String test) {
        return test.matches(NRIC_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
