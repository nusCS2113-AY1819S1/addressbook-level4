package seedu.recruit.model.joboffer.exceptions;

/**
 * Signals that the operation will result in duplicate candidates being shortlisted for the same job offer
 */

public class DuplicateShortlistedCandidateException extends RuntimeException {
    public DuplicateShortlistedCandidateException() {
        super("Operation would result in duplicate candidates being shortlisted for the same job offer");
    }
}
