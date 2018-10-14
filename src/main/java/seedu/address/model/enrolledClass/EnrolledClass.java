package seedu.address.model.enrolledClass;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class EnrolledClass {
    public static final String MESSAGE_ENROLLED_CLASS_CONSTRAINTS = "Enrolled Class names should be alphanumeric";
    public static final String ENROLLED_CLASS_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String enrolledClassName;

    public final String notesStoragePath;

    /**
     * Constructs a {@code enrolled class}.
     *
     * @param enrolledClassName A valid enrolled class name.
     */
    public EnrolledClass(String enrolledClassName) {
        requireNonNull(enrolledClassName);
        checkArgument(isValidEnRolledClassName(enrolledClassName), MESSAGE_ENROLLED_CLASS_CONSTRAINTS);
        this.enrolledClassName = enrolledClassName;
        this.notesStoragePath = "home/" + enrolledClassName;
    }

    /**
     * Returns true if a given string is a valid enrolled class name.
     */
    public static boolean isValidEnRolledClassName(String test) {
        return test.matches(ENROLLED_CLASS_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.enrolledClass.EnrolledClass // instanceof handles nulls
                && enrolledClassName.equals(((seedu.address.model.enrolledClass.EnrolledClass)other).enrolledClassName));
        // state check
    }

    @Override
    public int hashCode() {
        return enrolledClassName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + enrolledClassName + ']';
    }

}