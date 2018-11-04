package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.drink.Drink;

/**
 * Unmodifiable view of an inventory list.
 */
public interface ReadOnlyInventoryList {
    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Drink> getDrinkList();
}
