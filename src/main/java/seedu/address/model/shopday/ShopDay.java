package seedu.address.model.shopday;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.transaction.Transaction;

/**
 * TODO
 */
public class ShopDay {
    private static DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static LocalDateTime time;

    private TreeMap<String, Transaction> shopDayRecord;
    private TreeMap<String, Reminder> reminderRecord;
    private String date;
    private boolean isActiveDay;

    public ShopDay() {
        this.date = dayFormat.format(time.now());
        this.initialise();
    }

    public ShopDay(String date) {
        this.date = date;
        this.initialise();
    }

    public String getDay() {
        return this.date;
    }

    /**
     * todo
     * @param transaction
     * @throws ClosedShopDayException
     * @throws DuplicateTransactionException
     */
    public void addTransaction(Transaction transaction) throws ClosedShopDayException, DuplicateTransactionException {
        String transactionTime = transaction.getTime();
        if (!this.isActiveDay) {
            throw new ClosedShopDayException();
        }
        else if (shopDayRecord.containsKey(transactionTime)) {
            throw new DuplicateTransactionException();
        }
        else {
            shopDayRecord.put(transactionTime, transaction);
        }
    }

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

    private void initialise() {
        this.shopDayRecord = new TreeMap<>();
        this.reminderRecord = new TreeMap<>();
        this.openDay();
    }
}
