package seedu.address.model.workout;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workout's Difficulty in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDifficulty(String)}
 */
public class Difficulty {

    public static final String MESSAGE_DIFFICULTY_CONSTRAINTS =
            "Difficulty should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DIFFICULTY_VALIDATION_REGEX = ".*";

    public final String fullDifficulty;

    /**
     * Constructs a {@code Difficulty}.
     *
     * @param difficulty A valid difficulty.
     */
    public Difficulty(String difficulty) {
        requireNonNull(difficulty);
        checkArgument(isValidDifficulty(difficulty), MESSAGE_DIFFICULTY_CONSTRAINTS);
        fullDifficulty = difficulty;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDifficulty(String test) {
        return test.matches(DIFFICULTY_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullDifficulty;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Difficulty // instanceof handles nulls
                && fullDifficulty.equals(((Difficulty) other).fullDifficulty)); // state check
    }

    @Override
    public int hashCode() {
        return fullDifficulty.hashCode();
    }

}