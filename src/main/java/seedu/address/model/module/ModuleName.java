package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a module name of a Module in Trajectory
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleName(String)}
 */
public class ModuleName {

    public static final String MESSAGE_MODULE_NAME_CONSTRAINTS =
            "Module name should only contain alphanumeric characters, spaces, hyphens (-), and ampersands (&),"
            + " and it should not be blank.";

    /*
     * The first character of the module name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * The regex allows hyphens (-) for hyphenated words,
     * and ampersands (&) for shortening lengthy names with the word "and".
     * The 2 accepted symbols are not allowed as the first character of the module name.
     */
    private static final String MODULE_NAME_VALIDATION_REGEX = "^[a-zA-z0-9][a-zA-z0-9-& ]+$";

    public final String moduleName;

    /**
     * Constructs a {@code ModuleName}.
     * @param moduleName must be a valid module name.
     */
    public ModuleName(String moduleName) {
        requireNonNull(moduleName);
        checkArgument(isValidModuleName(moduleName), MESSAGE_MODULE_NAME_CONSTRAINTS);
        this.moduleName = moduleName;
    }

    /**
     * Returns true if the input is a valid module name.
     */
    public static boolean isValidModuleName(String input) {
        return input.matches(MODULE_NAME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.moduleName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleName // instanceof handles nulls
                && moduleName.equals(((ModuleName) other).moduleName)); // state check
    }
}
