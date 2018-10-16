package seedu.recruit.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's desired education in the recruit book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEducation(String)}
 */

public class Education {
    public static final String EDUCATION_VALIDATION_REGEX = "[\\p{ASCII}]+";

    public static final String MESSAGE_EDUCATION_CONSTRAINTS =
            "Education level should not be blank ";

    public final String value;

    public Education(String educationInput) {
        requireNonNull(educationInput);
        checkArgument(isValidEducation(educationInput), MESSAGE_EDUCATION_CONSTRAINTS);
        value = educationInput;
    }

    /**
     * Returns true if a given string is a valid education.
     */
    public static boolean isValidEducation(String test) {
        return test.matches(EDUCATION_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Education // instanceof handles nulls
                && value.equals(((Education) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
