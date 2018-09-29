package seedu.address.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's desired education in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEducation(String)}
 */

public class Education {
    public static final String EDUCATION_VALIDATION_REGEX = "[\\p{Alpha}]+";

    public static final String MESSAGE_EDUCATION_CONSTRAINTS =
            "Education level should only contain alphabetical letters and it should not be blank ";

    public final String education;

    public Education(String educationInput) {
        requireNonNull(educationInput);
        checkArgument(isValidEducation(educationInput), MESSAGE_EDUCATION_CONSTRAINTS);
        education = educationInput;
    }

    /**
     * Returns true if a given string is a valid education.
     */
    public static boolean isValidEducation(String test) {
        return test.matches(EDUCATION_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Education // instanceof handles nulls
                && education.equals(((Education) other).education)); // state check
    }

    @Override
    public int hashCode() {
        return education.hashCode();
    }
}
