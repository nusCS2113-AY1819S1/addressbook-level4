package seedu.address.model.clubbudget.exceptions;

/**
 * Signals that the operation will result in duplicate Club Budgets (Club budgets are considered duplicates if they have
 * the same identity).
 */
public class DuplicateFinalClubBudgetException extends RuntimeException {
    public DuplicateFinalClubBudgetException() {
        super("Operation would result in duplicate club budgets");
    }
}
