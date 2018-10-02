package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workout's Calories in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCalories(String)}
 */
public class Calories {

    public static final String MESSAGE_CALORIES_CONSTRAINTS =
            "Calories should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String CALORIES_VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullCalories;

    /**
     * Constructs a {@code Calories}.
     *
     * @param calories A valid calories.
     */
    public Calories(String calories) {
        requireNonNull(calories);
        checkArgument(isValidCalories(calories), MESSAGE_CALORIES_CONSTRAINTS);
        fullCalories = calories;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidCalories(String test) { return test.matches(CALORIES_VALIDATION_REGEX); }


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