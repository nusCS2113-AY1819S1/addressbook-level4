package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a module code of a Module in Trajectory
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {

    public static final String MESSAGE_MODULE_CODE_CONSTRAINT =
            "Module code should begin with 2 or 3 uppercase letters, followed by a 4-digit number and an optional"
            + " uppercase letter at the end.";

    /*
     * The first 2 or 3 (optional) characters must be uppercase letters.
     * The following 1 character must be an integer from 1-9 (0 is not valid!).
     * The subsequent 3 characters must be integers from 0-9.
     * Optionally, one more character may be added at the end to denote subtypes of a module.
     * This final character must be an uppercase letter.
     */
    private static final String MODULE_CODE_VALIDATION_REGEX = "^[A-Z]{2,3}[1-9][0-9]{3}[A-Z]?$";

    public final String moduleCode;

    /**
     * Constructs a {@code ModuleCode}.
     * @param moduleCode has to be a valid module code.
     */
    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_MODULE_CODE_CONSTRAINT);
        this.moduleCode = moduleCode;
    }

    /**
     * Returns true if the input is a valid module code
     */
    public static boolean isValidModuleCode(String input) {
        return input.matches(MODULE_CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && moduleCode.equals(((ModuleCode) other).moduleCode)); // state check
    }
}
