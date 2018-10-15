package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.drink.Drink;
import seedu.address.model.drink.UniqueDrinkList;

/**
 * Wraps all data at the inventory-list level
 * Duplicates are not allowed (by .isSameDrink comparison)
 */
public class InventoryList implements ReadOnlyInventoryList {
    private final UniqueDrinkList drinks;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        drinks = new UniqueDrinkList();
    }

    public InventoryList() {}

    /**
     * Creates an InventoryList using the Drinkss in the {@code toBeCopied}
     */
    public InventoryList(ReadOnlyInventoryList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the inventory list with {@code drinks}.
     * {@code drinks} must not contain duplicate drinks.
     */
    public void setDrinks(List<Drink> drinks) {
        this.drinks.setDrinks(drinks);
    }

    /**
     * Resets the existing data of this {@code InventoryList} with {@code newData}.
     */
    public void resetData(ReadOnlyInventoryList newData) {
        requireNonNull(newData);

        setDrinks(newData.getDrinkList());
    }

    //// person-level operations

    /**
     * Returns true if a drink with the same identity as {@code drinks} exists in the inventory list.
     */
    public boolean hasDrink(Drink drink) {
        requireNonNull(drink);
        return drinks.contains(drink);
    }

    /**
     * Adds a drink to the inventory list.
     * The drink must not already exist in the inventory list.
     */
    public void addDrink(Drink d) {
        drinks.add(d);
    }

    /**
     * Replaces the given drink {@code target} in the list with {@code editedDrink}.
     * {@code target} must exist in the inventory list.
     * The drink identity of {@code editedDrink} must not be the same as another existing drink in the address book.
     */
    public void updateDrink(Drink target, Drink editedDrink) {
        requireNonNull(editedDrink);

        drinks.setDrink(target, editedDrink);
    }

    /**
     * Removes {@code key} from this {@code InventoryList}.
     * {@code key} must exist in the inventory list.
     */
    public void removeDrink(Drink key) {
        drinks.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return drinks.asUnmodifiableObservableList().size() + " drinks";
        // TODO: refine later
    }

    @Override
    public ObservableList<Drink> getDrinkList() {
        return drinks.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InventoryList // instanceof handles nulls
                && drinks.equals(((InventoryList) other).drinks));
    }

    @Override
    public int hashCode() {
        return drinks.hashCode();
    }
}
