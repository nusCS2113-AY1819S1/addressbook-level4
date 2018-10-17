package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;

/**
 * Represents an enrollment size for the class in the app.
 */
public class Enrollment {
    private String value;

    public Enrollment() {
    }

    public Enrollment(String maxEnrollment) {
        requireNonNull(maxEnrollment);
        value = maxEnrollment;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Enrollment // instanceof handles nulls
                && value.equals(((Enrollment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
