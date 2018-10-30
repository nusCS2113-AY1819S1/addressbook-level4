package seedu.address.model.saleshistory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * This class handles the SalesHistory
 */
public class SalesHistoryManager extends SalesHistory {

    private Transaction lastTransaction;

    /**
     * Constructs this class given a {@link ReadOnlySalesHistory} object.
     * @param initialState
     */
    public SalesHistoryManager(ReadOnlySalesHistory initialState) {
        super(initialState);
    }

    /**
     * Adds a transaction to the SalesHistory.
     * @param transaction
     * @throws InvalidTimeFormatException
     * @throws DuplicateTransactionException
     */
    @Override
    public void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            DuplicateTransactionException {
        try {
            super.addTransaction(transaction);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (DuplicateTransactionException e) {
            throw e;
        }
        lastTransaction = transaction;
    }

    /**
     * Gets the transactions for a given day as a String.
     * @param day
     * @return
     * @throws InvalidTimeFormatException
     */
    public String getDaysTransactionsAsString(String day) throws InvalidTimeFormatException {
        ArrayList<Transaction> daysTransactions;
        try {
            daysTransactions = super.getDaysTransactions(day);
        } catch (InvalidTimeFormatException e) {
            throw e;
        }
        if (daysTransactions == null || daysTransactions.isEmpty()) {
            return "No transactions found on the specified date!";
        }

        StringBuilder ret = new StringBuilder();
        ret.append("TIMINGS FOR TRANSACTIONS ON " + day + "\n");
        for (Transaction transaction : daysTransactions) {
            ret.append(transaction.getTransactionTime() + "\n");
        }

        return ret.toString();
    }

    public Transaction getLastTransaction() {
        return lastTransaction;
    }

    /**
     * This method adds a reminder to the {@code SalesHistory}.
     * @param reminder
     * @throws InvalidTimeFormatException
     * @throws DuplicateReminderException
     */
    @Override
    public void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException {
        try {
            super.addReminder(reminder);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (DuplicateReminderException e) {
            throw e;
        }
    }

    /**
     * Removes a reminder from the {@code salesHistory}.
     * @param reminderTime
     * @throws InvalidTimeFormatException
     * @throws NoSuchElementException
     */
    public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {
        try {
            super.removeReminder(reminderTime);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    /**
     * Returns the reminders which are due in the active day.
     * @return reminder list.
     */
    public ArrayList<Reminder> getOverdueReminders() {
        final String currentTime = TimeIdentifiedClass.getCurrentDateAndTime();

        Set reminderSet = getReminderRecord().entrySet();
        Iterator it = reminderSet.iterator();

        ArrayList<Reminder> remindersToReturn = new ArrayList<>();

        // set to true in order to enter subsequent while-loop
        boolean isLesserTime = true;

        while (it.hasNext() && isLesserTime) {
            Map.Entry reminderEntry = (Map.Entry) it.next();
            String reminderTime = (String) reminderEntry.getKey();
            Reminder reminderToAdd = (Reminder) reminderEntry.getValue();

            // checking if reminder time is lesser than current time
            isLesserTime = (reminderTime.compareTo(currentTime) <= 0);
            if (isLesserTime) {
                remindersToReturn.add(reminderToAdd);
            }
        }
        return remindersToReturn;
    }

    /**
     * Returns the reminders which are due and have not been shown by the thread, and declares them as shown by the
     * thread.
     * @return reminder list.
     */
    public ArrayList<Reminder> getOverDueRemindersForThread() {
        final String currentTime = TimeIdentifiedClass.getCurrentDateAndTime();

        Set reminderSet = getReminderRecord().entrySet();
        Iterator it = reminderSet.iterator();

        ArrayList<Reminder> remindersToReturn = new ArrayList<>();

        // set to true to enter the following while block
        boolean isLesserTime = true;

        while (it.hasNext() && isLesserTime) {
            Map.Entry entry = (Map.Entry) it.next();
            String reminderTime = (String) entry.getKey();
            Reminder reminderToAdd = (Reminder) entry.getValue();

            // true if reminder time is lesser than or equal to the current time.
            isLesserTime = (reminderTime.compareTo(currentTime) <= 0);

            if (isLesserTime && !reminderToAdd.hasBeenShownByThread()) {
                remindersToReturn.add(reminderToAdd);
                reminderToAdd.declareAsShownByThread();
            }
        }
        return remindersToReturn;
    }
}
