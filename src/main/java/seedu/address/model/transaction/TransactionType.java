package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

/**
 * Represents the possible transaction types recorded in a transaction.
 */
public enum TransactionType {
    SALE("SALE"), IMPORT("IMPORT");

    private String value;

    TransactionType(String value) {
        requireNonNull(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }


}
