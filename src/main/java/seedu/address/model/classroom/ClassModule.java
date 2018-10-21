package seedu.address.model.classroom;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a module code in the app.
 */
public class ClassModule {
    public static final String MESSAGE_CLASSMODULE_CONSTRAINTS =
            "Module code should begin with 2 or 3 uppercase letters, followed by a 4-digit number and an optional"
                    + " uppercase letter at the end.";
    private static final String MESSAGE_CLASSMODULE_REGEX = "^[A-Z]{2,3}[1-9][0-9]{3}[A-Z]?$";
    private String value;

    public ClassModule(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidClassModule(moduleCode), MESSAGE_CLASSMODULE_CONSTRAINTS);
        value = moduleCode;
    }

    public static Boolean isValidClassModule(String test) {
        return test.matches(MESSAGE_CLASSMODULE_REGEX);
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
                || (other instanceof ClassModule // instanceof handles nulls
                && value.equals(((ClassModule) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
