package seedu.address.model.task;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

//@@author chelseyong
/**
 * Represents a Task's Module Code in the address book.
 * Guarantees: immutable;
 */
public class ModuleCode {
    public static final String MESSAGE_MODULE_CODE_CONSTRAINTS =
            "Module code must have AAXXXX where A is an alphabet and X is a number.";

    public final String moduleCode;

    public ModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument(isValidModuleCode(moduleCode), MESSAGE_MODULE_CODE_CONSTRAINTS);
        this.moduleCode = moduleCode.toUpperCase();
    }

    /**
     * If @param s module code is valid
     * @return true
     */
    public static boolean isValidModuleCode(String s) {
        if (s.length() != 6) {
            return false;
        }
        char firstLetter = s.charAt(0);
        char secondLetter = s.charAt(1);
        if (!(firstLetter >= 'A' && firstLetter <= 'Z' && secondLetter >= 'A' && secondLetter <= 'Z')) {
            return false;
        }
        for (int i = 2; i < 6; i++) {
            if (!(s.charAt(i) >= '0') || !(s.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return moduleCode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && moduleCode.equals(((ModuleCode) other).moduleCode)); // state check
    }

    @Override
    public int hashCode() {
        return moduleCode.hashCode();
    }
}
