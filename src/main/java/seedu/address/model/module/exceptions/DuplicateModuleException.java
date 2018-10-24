package seedu.address.model.module.exceptions;

/**
 * Indicates if an operation will result in duplicate modules.
 * Modules are considered to be duplicates if they have the same module code and module name.
 */
public class DuplicateModuleException extends Exception {
    public DuplicateModuleException() {
        super("Module already exists");
    }
}
