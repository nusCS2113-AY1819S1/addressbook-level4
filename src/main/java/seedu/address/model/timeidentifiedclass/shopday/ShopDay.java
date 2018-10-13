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
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

/**
 *
 */
public class ShopDay extends TimeIdentifiedClass {
    private static DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static LocalDateTime time;

    private TreeMap<String, Transaction> shopDayRecord;
    private TreeMap<String, Reminder> reminderRecord;
    private final String date;
    private boolean isActiveDay;

    public ShopDay() {
        this.date = dayFormat.format(time.now());
        this.initialise();
    }

    public ShopDay(String date) throws InvalidTimeFormatException {
        requireNonNull(date);
        if (isValidDateFormat(date)) {
            this.date = date;
            this.initialise();
        } else {
            throw new InvalidTimeFormatException();
        }
    }

    public String getDay() {
        return this.date;
    }

    /**
     * todo
     * @param transaction
     * @throws InvalidTimeFormatException
     * @throws ClosedShopDayException
     * @throws DuplicateTransactionException
     */
    public void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            ClosedShopDayException, DuplicateTransactionException {
        String transactionTime = transaction.getTime();
        if (!this.isActiveDay) {
            throw new ClosedShopDayException();
        } else if (shopDayRecord.containsKey(transactionTime)) {
            throw new DuplicateTransactionException();
        } else {
            shopDayRecord.put(transactionTime, transaction);
        }
    }

    // TODO: To be implemented.
    public void addReminder() {
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

    public void openDay() {
        this.isActiveDay = true;
    }

    public void closeDay() {
        this.isActiveDay = false;
    }

    /**
     *
     * @param date
     * @return
     */
    public static boolean isValidDateFormat(String date) {
        String[] splitDate = date.split("/");
        if (isValidYear(splitDate[0]) && isValidMonth(splitDate[1]) && isValidDay(splitDate[2])) {
            return true;
        }
        return false;
    }

    private void initialise() {
        this.shopDayRecord = new TreeMap<>();
        this.reminderRecord = new TreeMap<>();
        this.openDay();
    }
}
