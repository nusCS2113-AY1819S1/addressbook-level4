package seedu.address.model.timeidentifiedclass.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.transaction.exceptions.ClosedTransactionException;

/**
 * A basic Transaction class, where the Product is taken to be a string. This will be updated with actual Product
 * objects in a later version.
 */
public class Transaction extends TimeIdentifiedClass {
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static LocalDateTime time;

    private TreeMap<String, Integer> transactionRecord;
    private String transactionTime;
    private boolean isActiveTransaction;

    public Transaction() {
        this.transactionTime = timeFormatter.format(time.now());
        this.transactionRecord = new TreeMap<>();
        this.openTransaction();
    }

    public String getTime() {
        return transactionTime;
    }

    public void addProduct(String itemName) throws ClosedTransactionException {
        this.addProduct(itemName, 1);
    }

    /**
     * todo
     * @param itemName
     * @param quantity
     * @throws ClosedTransactionException
     */
    public void addProduct(String itemName, int quantity) throws ClosedTransactionException {
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

    public String getTransactionRecord() {
        StringBuilder ret = new StringBuilder();
        Set set = transactionRecord.entrySet();
        Iterator it = set.iterator();

        ret.append("================== Transaction Record " + this.getTime() + "==================\n");
        ret.append("ITEM\tQTY\n");
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ret.append(entry.getKey() + "\t\t" + entry.getValue() + "\n");
        }
        ret.trimToSize();
        return ret.toString();
    }
}
