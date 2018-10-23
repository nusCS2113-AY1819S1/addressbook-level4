package seedu.address.model.enrolledModule;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class EnrolledModule {
    public static final String MESSAGE_ENROLLED_MODULE_CONSTRAINTS = "Enrolled Module names should be alphanumeric";
    public static final String ENROLLED_MODULE_VALIDATION_REGEX = "\\p{Alnum}+";

    public final String enrolledModuleName;

    public final String notesStoragePath;

    /**
     * Constructs a {@code enrolled module}.
     *
     * @param enrolledModuleName A valid enrolled module name.
     */
    public EnrolledModule(String enrolledModuleName) {
        requireNonNull(enrolledModuleName);
        checkArgument(isValidEnRolledModuleName(enrolledModuleName), MESSAGE_ENROLLED_MODULE_CONSTRAINTS);
        this.enrolledModuleName = enrolledModuleName;
        this.notesStoragePath = "home/" + enrolledModuleName;
    }

    /**
     * Returns true if a given string is a valid enrolled module name.
     */
    public static boolean isValidEnRolledModuleName(String test) {
        return test.matches(ENROLLED_MODULE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EnrolledModule // instanceof handles nulls
                && enrolledModuleName.equals(((EnrolledModule)other).enrolledModuleName));
        // state check
    }

    @Override
    public int hashCode() {
        return enrolledModuleName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + enrolledModuleName + ']';
    }

}