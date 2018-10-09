package seedu.recruit.model.company.exceptions;

/**
 * Signals that the operation will result in duplicate companies
 */

public class DuplicateCompanyException extends RuntimeException {
    public DuplicateCompanyException() {
        super("Operation would result in duplicate companies");
    }
}
