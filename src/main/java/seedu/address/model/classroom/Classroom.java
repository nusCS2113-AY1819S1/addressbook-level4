package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;

/**
 * Represents a classroom in the app.
 */
public class Classroom {
    public final String value;

    public Classroom(String className) {
        requireNonNull(className);
        value = className;
    }
    @Override
    public String toString() {
        return value;
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Classroom // instanceof handles nulls
                && value.equals(((Classroom) other).value)); // state check
    }
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
