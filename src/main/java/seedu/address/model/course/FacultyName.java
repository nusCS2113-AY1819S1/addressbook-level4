package seedu.address.model.course;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the name of a given faculty, for individual courses.
 * Guarantees: immutable; is valid as declared in {@link #isValidFacultyName(String)}
 */
public class FacultyName {

    public static final String MESSAGE_COURSE_FACULTY_NAME_CONSTRAINTS =
            "Course codes may only contain alphanumeric characters and spaces, and it should not be blank";

    public static final String COURSE_FACULTY_NAME_VALIDATION_REGEX = "^[a-zA-Z\\s]*$";

    public final String facultyName;

    /**
     * Constructs a {@code FacultyName}.
     *
     * @param facultyName A valid course code.
     */
    public FacultyName(String facultyName) {
        requireNonNull(facultyName);
        checkArgument(isValidFacultyName(facultyName), MESSAGE_COURSE_FACULTY_NAME_CONSTRAINTS);
        this.facultyName = facultyName;
    }

    /**
     * Returns true if a given string is a valid course code.
     */
    public static boolean isValidFacultyName(String test) {
        return test.matches(COURSE_FACULTY_NAME_VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return facultyName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FacultyName // instanceof handles nulls
                && facultyName.equals(((FacultyName) other).facultyName)); // state check
    }

    @Override
    public int hashCode() {
        return facultyName.hashCode();
    }

}
