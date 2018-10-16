package seedu.address.model.expenditureinfo.exceptions;

/**
 * Signals that the operation will result in duplicate Expenditures
 * (Expenditures are considered duplicates if they have the same
 * identity fields).
 */
public class DuplicateExpenditureException extends RuntimeException {

    public DuplicateExpenditureException() {
            super("Operation would result in duplicate expenditures");
        }
}
