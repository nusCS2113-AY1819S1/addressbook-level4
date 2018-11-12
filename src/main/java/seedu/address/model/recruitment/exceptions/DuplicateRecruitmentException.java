package seedu.address.model.recruitment.exceptions;

/**
 * Signals that the operation will result in duplicate recruitments (Recruitments are considered duplicates if they
 * have the
 * same identity).
 */
public class DuplicateRecruitmentException extends RuntimeException {
    public DuplicateRecruitmentException() {
        super("Operation would result in duplicate recruitment");
    }
}
