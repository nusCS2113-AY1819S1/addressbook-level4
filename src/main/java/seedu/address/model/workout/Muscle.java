package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workout's Muscle in the workout book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMuscle(String)}
 */
public class Muscle {

    public static final String MESSAGE_MUSCLE_CONSTRAINTS =
            "Muscle should only contain names separated by commas, and it should not be blank.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String MUSCLE_VALIDATION_REGEX = "[\\p{Alpha}|,][\\p{Alpha} |, ]*";

    public final String fullMuscle;

    /**
     * Constructs a {@code Muscle}.
     *
     * @param muscle A valid muscle.
     */
    public Muscle(String muscle) {
        requireNonNull(muscle);
        checkArgument(isValidMuscle(muscle), MESSAGE_MUSCLE_CONSTRAINTS);
        fullMuscle = muscle;
    }

    /**
     * Returns true if a given string is a valid muscle.
     */
    public static boolean isValidMuscle(String test) {
        return test.matches(MUSCLE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullMuscle;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Muscle // instanceof handles nulls
                && fullMuscle.equals(((Muscle) other).fullMuscle)); // state check
    }

    @Override
    public int hashCode() {
        return fullMuscle.hashCode();
    }

}
