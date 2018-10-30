package seedu.address.model.saleshistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This class stores all the transactions and reminders. Each day and reminder must have a unique date.
 */
public class SalesHistory implements ReadOnlySalesHistory {
    private TreeMap<String, Transaction> transactionRecord;
    private TreeMap<String, Reminder> reminderRecord;
    private ObservableList<Transaction> transactionObservableList;
    private ObservableList<Reminder> reminderObservableList;
    /**
     * The following constructor creates a blank sales history.
     */
    public SalesHistory() {
        this.transactionRecord = new TreeMap<>();
        this.reminderRecord = new TreeMap<>();
        this.transactionObservableList = FXCollections.observableArrayList();
        this.reminderObservableList = FXCollections.observableArrayList();
    }

    /**
     * Creates {@code SalesHistory} given {@code transactionRecord} and {@code salesHistory}
     */
    public SalesHistory(TreeMap<String, Transaction> transactionRecord, TreeMap<String, Reminder> reminderRecord) {
        requireAllNonNull(transactionRecord, reminderRecord);
        this.transactionRecord = transactionRecord;
        this.reminderRecord = reminderRecord;
        this.transactionObservableList = FXCollections.observableArrayList();
        this.reminderObservableList = FXCollections.observableArrayList();

        Iterator it = reminderRecord.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            reminderObservableList.add((Reminder) entry.getValue());
        }

        it = transactionRecord.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            transactionObservableList.add((Transaction) entry.getValue());
        }

    }

    /**
     * Constructor using {@code ReadOnlySalesHistory} object.
     */
    public SalesHistory(ReadOnlySalesHistory toBeCopied) {
        this();
        requireNonNull(toBeCopied);

        for (Transaction transaction : toBeCopied.getTransactionsAsObservableList()) {
            try {
                addTransaction(transaction);
            } catch (InvalidTimeFormatException e) {
                e.printStackTrace();
            } catch (DuplicateTransactionException e) {
                e.printStackTrace();
            }
        }

        for (Reminder reminder : toBeCopied.getRemindersAsObservableList()) {
            try {
                addReminder(reminder);
            } catch (InvalidTimeFormatException e) {
                e.printStackTrace();
            } catch (DuplicateReminderException e) {
                e.printStackTrace();
            }
        }
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
        transactionObservableList.add(transaction);
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
        reminderObservableList.add(reminder);
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

        if (!Reminder.isValidReminderTime(reminderTime)) {
            throw new InvalidTimeFormatException();
        }

        if (!reminderRecord.containsKey(reminderTime)) {
            throw new NoSuchElementException();
        }

        Reminder toRemove = reminderRecord.get(reminderTime);
        reminderRecord.remove(reminderTime);
        reminderObservableList.remove(toRemove);
    }

    @Override
    public ObservableList<Transaction> getTransactionsAsObservableList() {
        return FXCollections.unmodifiableObservableList(transactionObservableList);
    }

    @Override
    public ObservableList<Reminder> getRemindersAsObservableList() {
        return FXCollections.unmodifiableObservableList(reminderObservableList);
    }
}
