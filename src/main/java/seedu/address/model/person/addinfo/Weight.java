package seedu.address.model.person.addinfo;

import static java.util.Objects.requireNonNull;

/**
 * Represents a person's Weight in Health Book.
 */
public class Weight {
    public static final String WEIGHT_VALIDATION_REGEX = "^(\\d+.)?\\d+";
    public static final String MESSAGE_WEIGHT_CONSTRAINTS = "Weight should be numeric input only.";

    public final String value;

    /**
     * Constructs an {@code Height}.
     *
     * @param weight A valid weight.
     */
    public Weight(String weight) {
        requireNonNull(weight);
        value = weight;
    }

    /**
     *
     * Returns if a given string is a valid Height
     */
    public static boolean isValidWeight(String test) {
        return test.matches(WEIGHT_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Weight
                && value.equals(((Weight) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
