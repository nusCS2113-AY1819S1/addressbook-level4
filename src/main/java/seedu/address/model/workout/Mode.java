package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workout's Mode in the workout book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMode(String)}
 */
public class Mode {

    public static final String MESSAGE_MODE_CONSTRAINTS = "Mode should only be either all, single or multiple INTEGER"
            + " where INTEGER is from 1 to 1000, inclusive of 1 and 1000.";

    public static final String MODE_VALIDATION_REGEX =
            "(all)|(single)|(multiple)(\\s+)([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|1000)";

    public final String fullMode;

    /**
     * Constructs a {@code Mode}.
     *
     * @param mode A valid mode value.
     */
    public Mode(String mode) {
        requireNonNull(mode);
        checkArgument(isValidMode(mode), MESSAGE_MODE_CONSTRAINTS);
        fullMode = mode;
    }

    /**
     * Returns true if a given string is a valid mode value.
     */
    public static boolean isValidMode(String test) {
        return test.matches(MODE_VALIDATION_REGEX);
    }

    /**
     * Returns the integer in the Multiple Mode string.
     */
    public int getMultipleInteger(String multipleMode) {
        return Integer.valueOf(multipleMode.split(" ")[1]);
    }

    public boolean isModeAll() {
        return fullMode.equals("all");
    }

    public boolean isModeSingle() {
        return fullMode.equals("single");
    }

    @Override
    public String toString() {
        return fullMode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Mode // instanceof handles nulls
                && fullMode.equals(((Mode) other).fullMode)); // state check
    }

    @Override
    public int hashCode() {
        return fullMode.hashCode();
    }

}
