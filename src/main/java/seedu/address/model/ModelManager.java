package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DRINKS;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.InventoryListChangedEvent;
import seedu.address.model.drink.Drink;

/**
 * Represents the in-memory model of the inventory list data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FilteredList<Drink> filteredDrinks;
    private final InventoryList inventoryList;

    /**
     * Initializes a ModelManager with the given inventoryList and userPrefs.
     */
    public ModelManager(ReadOnlyInventoryList inventoryList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(inventoryList, userPrefs);

        logger.fine("Initializing with inventory list: " + inventoryList + " and user prefs " + userPrefs);

        this.inventoryList = new InventoryList(inventoryList);
        filteredDrinks = new FilteredList<>(inventoryList.getDrinkList());
    }

    public ModelManager() {
        this(new InventoryList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyInventoryList newData) {
        inventoryList.resetData(newData);
        indicateInventoryListChanged();
    }

    @Override
    public ReadOnlyInventoryList getInventoryList() {
        return inventoryList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateInventoryListChanged() {
        raise(new InventoryListChangedEvent(inventoryList));
    }

    @Override
    public boolean hasDrink(Drink drink) {
        requireNonNull(drink);
        return inventoryList.hasDrink(drink);
    }

    @Override
    public void deleteDrink(Drink target) {
        inventoryList.removeDrink(target);
        indicateInventoryListChanged();
    }

    @Override
    public void addDrink(Drink drink) {
        inventoryList.addDrink(drink);
        updateFilteredDrinkList(PREDICATE_SHOW_ALL_DRINKS);
        indicateInventoryListChanged();
    }

    @Override
    public void updateDrink(Drink target, Drink editedDrink) {
        requireAllNonNull(target, editedDrink);

        inventoryList.updateDrink(target, editedDrink);
        indicateInventoryListChanged();
    }

    //=========== Filtered Drink List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Drink} backed by the internal list of
     * {@code inventoryList}
     */
    @Override
    public ObservableList<Drink> getFilteredDrinkList() {
        return FXCollections.unmodifiableObservableList(filteredDrinks);
    }

    @Override
    public void updateFilteredDrinkList(Predicate<Drink> predicate) {
        requireNonNull(predicate);
        filteredDrinks.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return inventoryList.equals(other.inventoryList)
                && inventoryList.equals(other.filteredDrinks);
    }
}
