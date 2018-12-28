package seedu.address.model.person.addinfo;

import static java.util.Objects.requireNonNull;

/**
 * Represents a person's height in Health Book
 */
public class Height {
    public static final String HEIGHT_VALIDATION_REGEX = "^(\\d+.)?\\d+";
    public static final String MESSAGE_HEIGHT_CONSTRAINTS = "Height should be numeric input only.";

    public final String value;

    /**
     * Constructs an {@code Height}.
     *
     * @param height A valid height.
     */
    public Height(String height) {
        requireNonNull(height);
        value = height;
    }

    /**
     *
     * Returns if a given string is a valid Height
     */
    public static boolean isValidHeight(String test) {
        return test.matches(HEIGHT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Height
                && value.equals(((Height) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
