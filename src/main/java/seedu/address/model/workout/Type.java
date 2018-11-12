package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workout's Type in the workout book.
 * Guarantees: immutable; is valid as declared in {@link #isValidType(String)}
 */
public class Type {

    public static final String MESSAGE_TYPE_CONSTRAINTS =
            "Types should only contain names separated by commas, and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String TYPE_VALIDATION_REGEX = "[\\p{Alpha}|,][\\p{Alpha} |, ]*";

    public final String fullType;

    /**
     * Constructs a {@code Type}.
     *
     * @param type A valid type.
     */
    public Type(String type) {
        requireNonNull(type);
        checkArgument(isValidType(type), MESSAGE_TYPE_CONSTRAINTS);
        fullType = type;
    }

    /**
     * Returns true if a given string is a valid type.
     */
    public static boolean isValidType(String test) {
        return test.matches(TYPE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && fullType.equals(((Type) other).fullType)); // state check
    }

    @Override
    public int hashCode() {
        return fullType.hashCode();
    }

}
