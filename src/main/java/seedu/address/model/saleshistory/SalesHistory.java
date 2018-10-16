package seedu.address.model.saleshistory;

import java.util.NoSuchElementException;
import java.util.TreeMap;

import seedu.address.model.saleshistory.exceptions.DuplicateDayException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.ShopDay;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

/**
 * TODO
 */
public class SalesHistory {
    private TreeMap<String, ShopDay> salesHistory;
    private ShopDay activeDay;

    public SalesHistory() {
        this.salesHistory = new TreeMap<>();

        // TODO: This is a stub for v1.2 . To be eliminated later.
        activeDay = new ShopDay();
        try {
            addDay(activeDay);
        } catch (DuplicateDayException e) {
            // This will not happen as list is initially empty.
        }
    }

    /**
     *
     * @param day
     * @throws DuplicateDayException
     */
    public void addDay(ShopDay day) throws DuplicateDayException {
        if (salesHistory.containsKey(day.getDay())) {
            throw new DuplicateDayException();
        }
        salesHistory.put(day.getDay(), day);
    }

    /**
     *
     * @param day
     * @throws DuplicateDayException
     * @throws InvalidTimeFormatException
     */
    public void addDay(String day) throws DuplicateDayException, InvalidTimeFormatException {
        if (salesHistory.containsKey(day)) {
            throw new DuplicateDayException();
        }
        ShopDay toBeAdded = null;
        try {
            toBeAdded = new ShopDay(day);
        } catch (InvalidTimeFormatException e) {
            throw e;
        }
    }

    public ShopDay getDaysHistory(String day) throws NoSuchElementException {
        try {
            return salesHistory.get(day);
        } catch (NullPointerException e) {
            throw new NoSuchElementException();
        }
    }

    public ShopDay getActiveDay() {
        // TODO: Finish this exception handling.
        try {
            return getDaysHistory(this.activeDay.getDay());
        } catch (NoSuchElementException e) {
            // just for stub: to be updated later.
            return null;
        }
    }

    public void setActiveDay(ShopDay day) {
        if (!salesHistory.containsKey(day.getDay())) {
            try {
                this.addDay(day);
            } catch (DuplicateDayException e) {
                // not possible since salesHistory does not contain the key.
            }
        }
        this.activeDay = salesHistory.get(day.getDay());
    }

    /**
     * TODO
     * @param transaction
     */
    public void addTransaction(Transaction transaction) {
        // TODO: Update the exception handling here.

        try {
            activeDay.addTransaction(transaction);
        } catch (InvalidTimeFormatException e) {
            // TODO

        } catch (ClosedShopDayException e) {
            //TODO
        } catch (DuplicateTransactionException e) {
            //TODO
        }

    }
}
