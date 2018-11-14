package seedu.address.model.timeidentifiedclass;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.timeidentifiedclass.exceptions.ClosedTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * A basic Transaction class, where the Product is taken to be a string. This will be updated with actual Product
 * objects in a later version.
 */
public class Transaction extends TimeIdentifiedClass {

    private TreeMap<String, Integer> transactionRecord;
    private String transactionTime;
    private boolean isActiveTransaction;

    public Transaction() {
        this.transactionTime = super.getCurrentDateAndTime();
        this.transactionRecord = new TreeMap<>();
        this.openTransaction();
    }

    /**
     * Constructor to create transaction with given parameters.
     * @param transactionTime
     * @param transactionRecord
     * @throws InvalidTimeFormatException
     */
    public Transaction(String transactionTime, TreeMap<String, Integer> transactionRecord)
            throws InvalidTimeFormatException {

        requireAllNonNull(transactionTime, transactionRecord);

        if (!isValidTransactionTime(transactionTime)) {
            throw new InvalidTimeFormatException();
        }
        this.transactionTime = transactionTime;
        this.transactionRecord = transactionRecord;
    }

    /**
     * Constructor to create a transaction object for a given time with an empty transaction record.
     * @param transactionTime
     * @throws InvalidTimeFormatException
     */
    public Transaction(String transactionTime) throws InvalidTimeFormatException {
        requireNonNull(transactionTime);

        if (!isValidTransactionTime(transactionTime)) {
            throw new InvalidTimeFormatException();
        }
        this.transactionTime = transactionTime;
        this.transactionRecord = new TreeMap<>();
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    /**
     * This method adds a product to the transaction.
     * @param itemName
     * @throws ClosedTransactionException
     */
    public void addProduct(String itemName) throws ClosedTransactionException {
        requireNonNull(itemName);
        this.addProduct(itemName, 1);
    }

    /**
     * Adds a product to the transaction record.
     * @param itemName
     * @param quantity
     * @throws ClosedTransactionException
     */
    public void addProduct(String itemName, int quantity) throws ClosedTransactionException {
        requireAllNonNull(itemName, quantity);
        if (!isActiveTransaction) {
            throw new ClosedTransactionException();
        } else if (transactionRecord.containsKey(itemName)) {
            transactionRecord.replace(itemName, transactionRecord.get(itemName) + quantity);
        } else {
            transactionRecord.put(itemName, quantity);
        }
    }

    public void closeTransaction() {
        this.isActiveTransaction = false;
    }

    public void openTransaction() {
        this.isActiveTransaction = true;
    }

    public TreeMap<String, Integer> getTransactionRecord() {
        return transactionRecord;
    }

    public String getTransactionRecordAsString() {
        StringBuilder ret = new StringBuilder();
        Set set = transactionRecord.entrySet();
        Iterator it = set.iterator();

        ret.append("================== Transaction Record " + this.getTransactionTime() + "==================\n");
        ret.append("PRODUCT NAME: QUANTITY\n");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getKey() != null && !entry.getKey().equals("")) {
                ret.append(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        }
        ret.trimToSize();
        return ret.toString();
    }

    /**
     * The following checks if a given string represents a valid transaction time.
     * @param transactionTime
     * @return true only if valid.
     */
    public static boolean isValidTransactionTime(String transactionTime) {
        transactionTime = transactionTime.trim();
        return isValidDateAndTime(transactionTime);
    }
}
