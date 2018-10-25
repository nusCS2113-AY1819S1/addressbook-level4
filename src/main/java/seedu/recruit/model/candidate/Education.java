package seedu.recruit.model.candidate;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.util.AppUtil.checkArgument;

/**
 * Represents a Candidate's desired education in the recruit book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEducation(String)}
 */

public class Education {

    public static final String MESSAGE_EDUCATION_CONSTRAINTS =
            "Education level indicated should be one of the following: "
            + "PRIMARY, "
            + "OLEVELS, "
            + "NLEVELS, "
            + "ALEVELS, "
            + "DIPLOMA, "
            + "BACHELOR, "
            + "MASTER, "
            + "PhD, "
            + "OTHERS";

    public final String value;
    public final int order;

    public Education(String educationInput) {
        requireNonNull(educationInput);
        checkArgument(isValidEducation(educationInput), MESSAGE_EDUCATION_CONSTRAINTS);
        value = educationInput;
        order = OrderOfEducation(educationInput);
    }

    public enum EducationLevel{
        PhD,
        MASTER,
        BACHELOR,
        DIPLOMA,
        ALEVELS,
        NLEVELS,
        OLEVELS,
        PRIMARY,
        OTHERS
    }

    /**
     * Returns true if a given string is a valid education.
     */
    public static boolean isValidEducation(String test) {
        for(EducationLevel e: EducationLevel.values()) {
            if(test.equals(e.name())) {
                return true;
            }
        }
        return false;
    }

    /** Determines the order of education input
     * @return order
     */
    public int OrderOfEducation(String input) {
        for(EducationLevel e: EducationLevel.values()) {
            if (input.equals(e.name())) {
                return e.ordinal();
            }
        }
        return -1;
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
