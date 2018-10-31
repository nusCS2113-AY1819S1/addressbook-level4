package seedu.address.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a course name, for individual courses.
 * Guarantees: immutable; is valid as declared in {@link #isValidCourseName(String)}
 */
public class CourseName {

    public static final String MESSAGE_COURSE_NAME_CONSTRAINTS =
            "Course names may only contain alphanumeric characters and spaces"
                    + ", may have between 1 to 64 characters and it should not be blank";

    public static final String COURSE_NAME_VALIDATION_REGEX = "^[a-zA-Z\\s]{1,64}$";

    public final String courseName;

    /**
     * Constructs a {@code CourseName}.
     *
     * @param courseName A valid course code.
     */
    public CourseName(String courseName) {
        requireNonNull(courseName);
        checkArgument(isValidCourseName(courseName), COURSE_NAME_VALIDATION_REGEX);
        this.courseName = courseName;
    }

    /**
     * Returns true if a given string is a valid course code.
     */
    public static boolean isValidCourseName(String test) {
        return test.matches(COURSE_NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return courseName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CourseName // instanceof handles nulls
                && courseName.equals(((CourseName) other).courseName)); // state check
    }

    @Override
    public int hashCode() {
        return courseName.hashCode();
    }

}
