package seedu.address.model.timeidentifiedclass.shopday;

import seedu.address.model.timeidentifiedclass.TimeIdentifiedClass;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import static java.util.Objects.requireNonNull;

public class ShopDay extends TimeIdentifiedClass {
    private static DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static LocalDateTime time;

    private TreeMap<String,Transaction> shopDayRecord;
    private TreeMap<String,Reminder> reminderRecord;
    private final String date;
    private boolean isActiveDay;

    private void initialise() {
        this.shopDayRecord = new TreeMap<>();
        this.reminderRecord = new TreeMap<>();
        this.openDay();
    }

    public ShopDay() {
        this.date = dayFormat.format(time.now());
        this.initialise();
    }

    public ShopDay(String date) {
        requireNonNull(date);
        this.date = date;
        this.initialise();
    }

    public String getDay() {
        return this.date;
    }

    public void addTransaction(Transaction transaction) throws ClosedShopDayException,DuplicateTransactionException {
        String transactionTime = transaction.getTime();
        if (!this.isActiveDay) throw new ClosedShopDayException();
        else if (shopDayRecord.containsKey(transactionTime)) throw new DuplicateTransactionException();
        else {
            shopDayRecord.put(transactionTime,transaction);
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

    public void closeDay(){
        this.isActiveDay = false;
    }

    private boolean isValidDateFormat(String date) {
        String[] splitDate = date.split("/");
        if (isValidYear(splitDate[0]) && isValidMonth(splitDate[1]) && isValidDay(splitDate[2])) {
            return true;
        }
        return false;
    }
}