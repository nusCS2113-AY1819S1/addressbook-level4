package seedu.address.model.drink;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.drink.exceptions.DrinkNotFoundException;
import seedu.address.model.drink.exceptions.DuplicateDrinkException;
import seedu.address.model.drink.exceptions.DuplicateNameException;

/**
 * A list of drinks that enforces uniqueness between its elements and does not allow nulls.
 * A drink is considered unique by comparing using {@code Drink#isSameDrink(Drink)}. As such, adding and updating of
 * drinks uses Drink#isSameDrink(Drink) for equality so as to ensure that the drink being added or updated is
 * unique in terms of identity in the UniqueDrinkList. However, the removal of a drink uses Drink#equals(Object) so
 * as to ensure that the drink with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Drink#isSameDrink(Drink)
 */
public class UniqueDrinkList implements Iterable<Drink> {

    private final ObservableList<Drink> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent drink as the given argument.
     */
    public boolean contains(Drink toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDrink);
    }

    /**
     * Adds a drink to the list.
     * The drink must not already exist in the list.
     */
    public void add(Drink toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDrinkException();
        }
        internalList.add(toAdd);
    }


    /**
     * Edits the {@code target} drink's name attribute.
     * {@code target} must exist in the list.
     * The new name {@code editedName} must not be the same as another existing drink in the list.
     */
    public void editDrinkName(Drink target, Name editedName) {
        requireAllNonNull(target, editedName);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DrinkNotFoundException();
        }

        if (!drinkNameIsUnique(editedName)) {
            throw new DuplicateNameException();
        }

        internalList.get(index).setName(editedName);
    }

    /**
     * Returns true if (@code editedName} is a unique name.
     */
    private boolean drinkNameIsUnique(Name editedName) {
        for (int i = 0; i < internalList.size(); i++) {
            if (internalList.get(i).getName().equals(editedName)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Removes the equivalent drink from the list.
     * The drink must exist in the list.
     */
    public void remove(Drink toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DrinkNotFoundException();
        }
    }

    public void setDrinks(UniqueDrinkList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code drinks}.
     * {@code drinks} must not contain duplicate drinks.
     */
    public void setDrinks(List<Drink> drinks) {
        requireAllNonNull(drinks);
        if (!drinksAreUnique(drinks)) {
            throw new DuplicateDrinkException();
        }

        internalList.setAll(drinks);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Drink> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Drink> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueDrinkList // instanceof handles nulls
                && internalList.equals(((UniqueDrinkList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code drinks} contains only unique drinks.
     */
    private boolean drinksAreUnique(List<Drink> drinks) {
        for (int i = 0; i < drinks.size() - 1; i++) {
            for (int j = i + 1; j < drinks.size(); j++) {
                if (drinks.get(i).isSameDrink(drinks.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
