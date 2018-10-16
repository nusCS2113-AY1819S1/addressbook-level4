package seedu.recruit.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's gender in the recruit book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */

public class Gender {
    public static final String GENDER_VALIDATION_REGEX = "[FM]";

    public static final String MESSAGE_GENDER_CONSTRAINTS =
            "Gender is restricted to either M(Male) or F(Female), and it should not be blank ";

    public final String value;

    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_GENDER_CONSTRAINTS);
        value = gender;
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String test) {
        return test.matches(GENDER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Gender // instanceof handles nulls
                && value.equals(((Gender) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
