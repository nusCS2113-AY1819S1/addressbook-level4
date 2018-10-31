package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.saleshistory.SalesHistory;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;

/**
 * JAXB-friendly version of {@code SalesHistory}
 */
@XmlRootElement(name = "SalesHistory")
public class XmlSerializableSalesHistory {

    private static final String DUPLICATE_TIMINGS_MESSAGE = "Duplicate %s timings found!";

    @XmlElement
    private List<XmlAdaptedTransaction> transactionList;
    @XmlElement
    private List<XmlAdaptedReminder> reminderList;

    /**
     * Constructs an {@code XmlSerializableSalesHistory}.
     * This is the no-arg constructor that is required for marshalling.
     */
    public XmlSerializableSalesHistory() {
        transactionList = new ArrayList<>();
        reminderList = new ArrayList<>();
    }

    /**
     * Constructs an {@code XmlSerializableSalesHistory} with the required details.
     */
    public XmlSerializableSalesHistory(List<XmlAdaptedTransaction> transactionList,
                                       List<XmlAdaptedReminder> reminderList) {
        if (transactionList != null) {
            this.transactionList = new ArrayList<>(transactionList);
        }
        if (reminderList != null) {
            this.reminderList = new ArrayList<>(reminderList);
        }
    }

    public XmlSerializableSalesHistory(ReadOnlySalesHistory src) {
        this();
        for (Transaction transaction : src.getTransactionsAsObservableList()) {
            transactionList.add(new XmlAdaptedTransaction(transaction));
        }
        for (Reminder reminder : src.getRemindersAsObservableList()) {
            reminderList.add(new XmlAdaptedReminder(reminder));
        }
    }

    /**
     * Converts a given SalesHistory object into this class for JAXB use.
     * @param salesHistory
     */
    public XmlSerializableSalesHistory(SalesHistory salesHistory) {
        transactionList = new ArrayList<>();
        reminderList = new ArrayList<>();

        Set transactionSet = salesHistory.getTransactionRecord().entrySet();
        Iterator it = transactionSet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            XmlAdaptedTransaction toAdd = new XmlAdaptedTransaction((Transaction) entry.getValue());
            transactionList.add(toAdd);
        }

        Set reminderSet = salesHistory.getReminderRecord().entrySet();
        it = reminderSet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            XmlAdaptedReminder toAdd = new XmlAdaptedReminder((Reminder) entry.getValue());
            reminderList.add(toAdd);
        }
    }

    /**
     * Converts object of this class into {@code SalesHistory} class
     * @return salesHistory
     * @throws IllegalValueException
     */
    public SalesHistory toModelType() throws IllegalValueException {
        TreeMap<String, Transaction> transactionTreeMap = new TreeMap<>();
        TreeMap<String, Reminder> reminderTreeMap = new TreeMap<>();

        for (XmlAdaptedTransaction xmlTransaction : transactionList) {
            Transaction transaction = xmlTransaction.toModelType();
            if (transactionTreeMap.containsKey(transaction.getTransactionTime())) {
                throw new IllegalValueException(String.format(DUPLICATE_TIMINGS_MESSAGE,
                        Transaction.class.getSimpleName()));
            }
            transactionTreeMap.put(transaction.getTransactionTime(), transaction);
        }

        for (XmlAdaptedReminder xmlReminder : reminderList) {
            Reminder reminder = xmlReminder.toModelType();
            if (reminderTreeMap.containsKey(reminder.getReminderTime())) {
                throw new IllegalValueException(String.format(DUPLICATE_TIMINGS_MESSAGE,
                        Reminder.class.getSimpleName()));
            }
            reminderTreeMap.put(reminder.getReminderTime(), reminder);
        }

        return new SalesHistory(transactionTreeMap, reminderTreeMap);
    }
}
