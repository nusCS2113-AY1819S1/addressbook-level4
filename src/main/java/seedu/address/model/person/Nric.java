package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a person's NRIC in Health Book.
 */
public class Nric {
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
