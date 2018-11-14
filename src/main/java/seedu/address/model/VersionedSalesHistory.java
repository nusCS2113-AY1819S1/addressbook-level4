package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.saleshistory.SalesHistory;
import seedu.address.model.saleshistory.SalesHistoryManager;

/**
 * {@link SalesHistoryManager} that keeps track fo its own history.
 */
public class VersionedSalesHistory extends SalesHistoryManager {
    private final List<ReadOnlySalesHistory> salesHistoriesStateList;
    private int currentStatePointer;

    public VersionedSalesHistory(ReadOnlySalesHistory initialState) {
        super(initialState);
        salesHistoriesStateList = new ArrayList<>();
        salesHistoriesStateList.add(initialState);
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code giSalesHistory} state at the end of the state list
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        salesHistoriesStateList.add(new SalesHistory(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        salesHistoriesStateList.subList(currentStatePointer + 1, salesHistoriesStateList.size()).clear();;
    }
}
