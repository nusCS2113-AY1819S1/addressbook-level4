package seedu.address.model.scorelist.exceptions;

/**
 * Signals that the operation will result in duplicate SCORES (Scores are considered duplicates if they have the same
 * identity).
 */
public class DuplicateScoreException extends RuntimeException {
    public DuplicateScoreException() {
        super("Operation would result in duplicate scores");
    }
}
