package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workout's Calories in the workout book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCalories(String)}
 */
public class Calories {

    public static final String MESSAGE_CALORIES_CONSTRAINTS = "Calories must a positive integer"
            + " from 1 to 1000, inclusive of 1 and 1000.";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CALORIES_VALIDATION_REGEX =
            "([1-9]|[1-8][0-9]|9[0-9]|[1-8][0-9]{2}|9[0-8][0-9]|99[0-9]|1000)";

    public final String fullCalories;

    /**
     * Constructs a {@code Calories}.
     *
     * @param calories A valid calories value.
     */
    public Calories(String calories) {
        requireNonNull(calories);
        checkArgument(isValidCalories(calories), MESSAGE_CALORIES_CONSTRAINTS);
        fullCalories = calories;
    }

    /**
     * Returns true if a given string is a valid calories value.
     */
    public static boolean isValidCalories(String test) {
        return test.matches(CALORIES_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullCalories;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Calories // instanceof handles nulls
                && fullCalories.equals(((Calories) other).fullCalories)); // state check
    }

    @Override
    public int hashCode() {
        return fullCalories.hashCode();
    }

}
