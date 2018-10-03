package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.item.Item;

/**
 * Unmodifiable view of a stock list
 */
public interface ReadOnlyStockList {

    /**
     * Returns an unmodifiable view of the items list.
     * This list will not contain any duplicate items.
     */
    ObservableList<Item> getItemList();

}
