package seedu.address.model.timeidentifiedclass.shopday;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

/**
 * This class represents one day of operation in the store, and contains the transactions
 * and reminders of that day.
 */
public class ShopDay extends TimeIdentifiedClass {
    private static DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static LocalDateTime time;

    private TreeMap<String, Transaction> shopDayRecord;
    private TreeMap<String, Reminder> reminderRecord;
    private final String date;
    private boolean isActiveDay;

    /**
     * The following are the class constructors.
     */

    public ShopDay() {
        this.date = dayFormat.format(time.now());
        this.initialise();
    }

    /**
     * This constructor allows us to create a new ShopDay object using a date. It is to facilitate
     * creation of reminders.
     * @param date
     * @throws InvalidTimeFormatException
     */
    public ShopDay(String date) throws InvalidTimeFormatException {
        requireNonNull(date);
        if (isValidDateFormat(date)) {
            this.date = date;
            this.initialise();
        } else {
            throw new InvalidTimeFormatException();
        }
    }

    /**
     * The following constructor is to be used to facilitate reading from files.
     * @param date
     * @param shopDayRecord
     * @param reminderRecord
     * @throws InvalidTimeFormatException
     */

    public ShopDay(String date, TreeMap<String, Transaction> shopDayRecord, TreeMap<String, Reminder> reminderRecord)
            throws InvalidTimeFormatException {
        if (isValidDateFormat(date)) {
            this.date = date;
            this.shopDayRecord = shopDayRecord;
            this.reminderRecord = reminderRecord;
        } else {
            throw new InvalidTimeFormatException();
        }
    }

    /**
     * The following method is the all-purpose initializer for a new shopDay.
     */

    private void initialise() {
        this.shopDayRecord = new TreeMap<>();
        this.reminderRecord = new TreeMap<>();
        this.openDay();
    }


    public String getDay() {
        return this.date;
    }

    /**
     * The following method adds a transaction to the given ShopDay object.
     * @param transaction
     * @throws InvalidTimeFormatException
     * @throws ClosedShopDayException
     * @throws DuplicateTransactionException
     */

    public void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            ClosedShopDayException, DuplicateTransactionException {
        String transactionTime = transaction.getTransactionTime();
        if (!Transaction.isValidTransactionTime(transactionTime)) {
            throw new InvalidTimeFormatException();
        }
        if (!this.isActiveDay) {
            throw new ClosedShopDayException();
        } else if (shopDayRecord.containsKey(transactionTime)) {
            throw new DuplicateTransactionException();
        } else {
            shopDayRecord.put(transactionTime, transaction);
        }
    }

    /**
     * The following method adds a reminder to the reminder record.
     * @param reminder
     */
    public void addReminder(Reminder reminder) throws DuplicateReminderException {
        if (reminderRecord.containsKey(reminder.getTime())
                && reminderRecord.get(reminder.getTime()).getMessage().equalsIgnoreCase(reminder.getMessage())) {
            throw new DuplicateReminderException();
        }
        reminderRecord.put(reminder.getTime(), reminder);
    }

    /**
     * The following method removes a reminder from the reminder record.
     * @param reminder
     */

    public void removeReminder(Reminder reminder) {
        reminderRecord.remove(reminder.getTime());
    }

    public String getDaysTransactions() {
        StringBuilder ret = new StringBuilder();

        ret.append("================== Day Record for " + this.getDay() + "==================\n");
        ret.append("TRANSACTION TIMINGS\n");

        Set set = shopDayRecord.entrySet();
        Iterator it = set.iterator();

        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ret.append(entry.getKey() + "\n");
        }
        ret.trimToSize();
        return ret.toString();
    }

    /**
     * Obtain the reminder record.
     * @return reminderRecord
     */

    public TreeMap<String, Reminder> getReminderRecord() {
        return reminderRecord;
    }

    public void openDay() {
        this.isActiveDay = true;
    }

    public void closeDay() {
        this.isActiveDay = false;
    }

    public boolean isOpenDay() {
        return isActiveDay;
    }

    /**
     * The following method checks whether a string is in the required date format.
     * @param date
     * @return true if valid format, false otherwise.
     */

    public static boolean isValidDateFormat(String date) {
        String[] splitDate = date.split("/");
        if (isValidYear(splitDate[0]) && isValidMonth(splitDate[1]) && isValidDay(splitDate[2])) {
            return true;
        }
        return false;
    }
}
