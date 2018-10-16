package seedu.address.model.transaction;

/**
 * Represents the possible transaction types recorded in a transaction.
 */
public enum TransactionType {
    SALE, IMPORT;

    @Override
    public String toString() {
        return "Sale, Import";
    }



}
