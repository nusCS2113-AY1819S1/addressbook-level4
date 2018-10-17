package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;

/**
 * Represents a classname.
 */
public class ClassName {
    private String value;

    public ClassName(String className) {
        requireNonNull(className);
        value = className;
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
                || (other instanceof ClassName // instanceof handles nulls
                && value.equals(((ClassName) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
