package seedu.address.storage;

import java.util.Objects;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

/**
 * This class adapts Transaction for JAXB.
 */

public class XmlAdaptedTransaction {

    private static final String INCORRECT_TRANSACTION_TIME_MESSAGE_FORMAT = "Incorrect transaction time of %s found!";
    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Transaction's %s field is missing!";

    @XmlElement(required = true)
    private String transactionTime;
    @XmlElement(required = true)
    private TreeMap<String, Integer> transactionRecord;

    /**
     * Constructs an XmlAdaptedTransaction.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTransaction() {
    }

    /**
     * Constructs an {@code XmlAdaptedTransaction} with the required transaction details.
     *
     * @param transactionTime
     * @param transactionRecord
     */

    public XmlAdaptedTransaction(String transactionTime, TreeMap<String, Integer> transactionRecord) {
        this.transactionTime = transactionTime;
        this.transactionRecord = transactionRecord;

    }

    /**
     * Converts a given transaction into this class for JAXB use.
     *
     * @param transaction
     */

    public XmlAdaptedTransaction(Transaction transaction) {
        transactionTime = transaction.getTransactionTime();
        transactionRecord = transaction.getTransactionRecord();
    }

    /**
     * This method converts the XmlAdaptedTransaction into a Transacation object, and returns it.
     * @return transaction
     * @throws IllegalValueException
     */

    public Transaction toModelType() throws IllegalValueException {

        if (transactionRecord == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "transaction records"));
        }

        Transaction transaction;

        try {
            transaction = new Transaction(transactionTime, transactionRecord);
        } catch (InvalidTimeFormatException e) {
            throw new IllegalValueException(String.format(INCORRECT_TRANSACTION_TIME_MESSAGE_FORMAT,
                    "transaction time"));
        }
        return transaction;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTransaction)) {
            return false;
        }

        XmlAdaptedTransaction otherTransaction = (XmlAdaptedTransaction) other;
        return Objects.equals(transactionRecord, otherTransaction.transactionRecord)
                && Objects.equals(transactionTime, otherTransaction.transactionTime);
    }

}
