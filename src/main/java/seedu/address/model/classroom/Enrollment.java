package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an enrollment size for the class in the app.
 */
public class Enrollment {
    public static final String MESSAGE_ENROLLMENT_CONSTRAINTS =
            "Class enrollment size should only contain between 1 to 425 numeric characters"
                    + " and it should not be blank.";
    private static final String MESSAGE_ENROLLMENT_REGEX = "^([1-9]|[1-9][0-9]|[1-4][0-2][0-5])$";
    private String value;

    public Enrollment() {
    }

    public Enrollment(String maxEnrollment) {
        requireNonNull(maxEnrollment);
        checkArgument(isValidEnrollment(maxEnrollment), MESSAGE_ENROLLMENT_CONSTRAINTS);
        value = maxEnrollment;
    }

    public static Boolean isValidEnrollment(String test) {
        return test.matches(MESSAGE_ENROLLMENT_REGEX);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Enrollment // instanceof handles nulls
                && value.equals(((Enrollment) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
