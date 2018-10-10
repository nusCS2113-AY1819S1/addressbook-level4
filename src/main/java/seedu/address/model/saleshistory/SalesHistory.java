package seedu.address.model.saleshistory;

import seedu.address.model.saleshistory.exceptions.DuplicateDayException;
import seedu.address.model.timeidentifiedclass.shopday.ShopDay;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class SalesHistory {
    private TreeMap<String,ShopDay> salesHistory;

    public SalesHistory() {
        this.salesHistory = new TreeMap<>();
    }

    public void addDay(String day) throws DuplicateDayException {
        if (salesHistory.containsKey(day)) throw new DuplicateDayException();
        ShopDay toBeAdded = new ShopDay(day);
        salesHistory.put(day,toBeAdded);
    }

    public ShopDay getDaysHistory(String day) throws NoSuchElementException {
        return salesHistory.get(day);
    }
}
