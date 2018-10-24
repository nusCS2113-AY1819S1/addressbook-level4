package seedu.address.model.saleshistory;

import java.util.NoSuchElementException;
import java.util.TreeMap;

import seedu.address.model.saleshistory.exceptions.DuplicateDayException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.BusinessDay;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;

/**
 * This class stores all the shopDay objects, with their contained transactions. Each day must have a unique date.
 */
public class SalesHistory {
    private TreeMap<String, BusinessDay> salesHistory;
    private BusinessDay activeDay;

    /**
     * The following constructor creates a blank sales history, with today automatically inserted.
     */

    public SalesHistory() {
        this.salesHistory = new TreeMap<>();
        activeDay = new BusinessDay();
        salesHistory.put(activeDay.getDay(), activeDay);
    }

    /**
     * The following constructor is to facilitate reading sales history from files.
     * @param salesHistory
     */

    public SalesHistory(TreeMap<String, BusinessDay> salesHistory) {
        this.salesHistory = salesHistory;
        BusinessDay today = new BusinessDay();
        if (!salesHistory.containsKey(today.getDay())) {
            salesHistory.put(today.getDay(), today);
        }
    }

    /**
     * This method adds a day to the sales history.
     * @param day
     * @throws DuplicateDayException
     */
    public void addDay(BusinessDay day) throws DuplicateDayException {
        if (salesHistory.containsKey(day.getDay())) {
            throw new DuplicateDayException();
        }
        salesHistory.put(day.getDay(), day);
    }

    /**
     * The following method adds a day to the sales history.
     * @param day
     * @throws DuplicateDayException
     * @throws InvalidTimeFormatException
     */
    public void addDay(String day) throws DuplicateDayException,
            InvalidTimeFormatException {
        if (salesHistory.containsKey(day)) {
            throw new DuplicateDayException();
        }
        BusinessDay toBeAdded = null;
        try {
            toBeAdded = new BusinessDay(day);
        } catch (InvalidTimeFormatException e) {
            throw e;
        }
    }

    public BusinessDay getDaysHistory(String day) throws NoSuchElementException {
        try {
            return salesHistory.get(day);
        } catch (NullPointerException e) {
            throw new NoSuchElementException();
        }
    }

    public BusinessDay getActiveDay() throws NoSuchElementException {
        try {
            return getDaysHistory(activeDay.getDay());
        } catch (NoSuchElementException e) {
            throw e;
        }
    }

    public void setActiveDay(BusinessDay day) {
        try {
            addDay(day);
            activeDay = salesHistory.get(day.getDay());
        } catch (DuplicateDayException e) {
            activeDay = salesHistory.get(day.getDay());
        }
    }


    /**
     * The following method adds a transaction to the active day.
     * @param transaction
     */
    public void addTransaction(Transaction transaction) throws
            InvalidTimeFormatException,
            ClosedShopDayException,
            DuplicateTransactionException {
        try {
            activeDay.addTransaction(transaction);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (ClosedShopDayException e) {
            throw e;
        } catch (DuplicateTransactionException e) {
            throw e;
        }
    }
}
