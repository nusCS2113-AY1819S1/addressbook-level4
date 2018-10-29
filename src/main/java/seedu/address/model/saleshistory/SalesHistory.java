package seedu.address.model.saleshistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This class stores all the transactions and reminders. Each day and reminder must have a unique date.
 */
public class SalesHistory {
    private TreeMap<String, Transaction> transactionRecord;
    private TreeMap<String, Reminder> reminderRecord;

    /**
     * The following constructor creates a blank sales history.
     */
    public SalesHistory() {
        this.transactionRecord = new TreeMap<>();
        this.reminderRecord = new TreeMap<>();
    }

    /**
     * The following constructor is to facilitate reading sales history from files.
     * @param transactionRecord
     */
    public SalesHistory(TreeMap<String, Transaction> transactionRecord, TreeMap<String, Reminder> reminderRecord) {
        requireAllNonNull(transactionRecord, reminderRecord);
        this.transactionRecord = transactionRecord;
        this.reminderRecord = reminderRecord;
    }

    public TreeMap<String, Transaction> getTransactionRecord() {
        return transactionRecord;
    }

    public TreeMap<String, Reminder> getReminderRecord() {
        return reminderRecord;
    }

    public ArrayList<Transaction> getDaysTransactions(String day) throws InvalidTimeFormatException {
        requireNonNull(day);
        day = day.trim();

        if (!TimeIdentifiedClass.isValidDay(day)) {
            throw new InvalidTimeFormatException();
        }

        final String initialTime = day + " 00:00:00";
        final String finalTime = day + " 24:00:00";

        // To get the day's transactions...
        ArrayList<Transaction> daysTransactions = new ArrayList<>();
        Set transactionSet = transactionRecord.subMap(initialTime, finalTime).entrySet();
        Iterator it = transactionSet.iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            daysTransactions.add((Transaction) entry.getValue());
        }
        return daysTransactions;
    }

    /**
     * The following method adds a transaction with a valid and unique time to the {@code transactionRecord}.
     * @param transaction
     * @throws InvalidTimeFormatException
     * @throws DuplicateTransactionException
     */
    public void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            DuplicateTransactionException {
        requireNonNull(transaction);
        if (!Transaction.isValidTransactionTime(transaction.getTransactionTime())) {
            throw new InvalidTimeFormatException();
        }
        if (transactionRecord.containsKey(transaction.getTransactionTime())) {
            throw new DuplicateTransactionException();
        }
        transactionRecord.put(transaction.getTransactionTime(), transaction);
    }

    /**
     * The following method adds a {@code reminder} with a valid and unique time to the {@code reminderRecord}.
     * @param reminder
     * @throws InvalidTimeFormatException
     * @throws DuplicateReminderException
     */
    public void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException {
        requireNonNull(reminder);
        if (!Reminder.isValidReminderTime(reminder.getReminderTime())) {
            throw new InvalidTimeFormatException();
        }
        if (reminderRecord.containsKey(reminder.getReminderTime())) {
            throw new DuplicateReminderException();
        }
        reminderRecord.put(reminder.getReminderTime(), reminder);
    }

    /**
     * Removes given {@code reminder} from the sales history.
     * @param reminderTime
     * @throws InvalidTimeFormatException
     * @throws NoSuchElementException
     */
    public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {
        requireNonNull(reminderTime);
        reminderTime = reminderTime.trim();

        if (!reminderRecord.containsKey(reminderTime)) {
            throw new NoSuchElementException();
        }
        if (!Reminder.isValidReminderTime(reminderTime)) {
            throw new InvalidTimeFormatException();
        }
        reminderRecord.remove(reminderTime);
    }
}
