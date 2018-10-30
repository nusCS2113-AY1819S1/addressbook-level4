package seedu.address.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a course code, for individual courses.
 * Guarantees: immutable; is valid as declared in {@link #isValidCourseCode(String)}
 */
public class CourseCode {

    public static final String MESSAGE_COURSE_CODE_CONSTRAINTS =
            "Course codes may only contain alphanumeric characters and spaces, can have between 1 to 10 characters, and it should not be blank";

    public static final String COURSE_CODE_VALIDATION_REGEX = "^[a-zA-Z\\s]{1,10}$";

    public final String courseCode;

    /**
     * Constructs a {@code CourseCode}.
     *
     * @param courseCode A valid course code.
     */
    public CourseCode(String courseCode) {
        requireNonNull(courseCode);
        checkArgument(isValidCourseCode(courseCode), MESSAGE_COURSE_CODE_CONSTRAINTS);
        this.courseCode = courseCode;
    }

    /**
     * Returns true if a given string is a valid course code.
     */
    public static boolean isValidCourseCode(String test) {
        return test.matches(COURSE_CODE_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return courseCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CourseCode // instanceof handles nulls
                && courseCode.equals(((CourseCode) other).courseCode)); // state check
    }

    @Override
    public int hashCode() {
        return courseCode.hashCode();
    }

}
