package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.Product;
import seedu.address.model.login.Password;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.person.Product;
import seedu.address.model.distributor.Distributor;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */

    Predicate<Distributor> PREDICATE_SHOW_ALL_DISTRIBUTORS = unused -> true;

    Predicate<Product> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getProductInfoBook();

    /** Returns the AddressBook */
    ReadOnlyAddressBook getDistributorInfoBook();

    /**
     * Returns true if a distributor with the same identity as {@code distributor} exists in the Inventarie.
     */
    boolean hasDistributor(Distributor distributor);

    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    boolean hasPerson(Product product);

    /**
     * Deletes the given distributor.
     * The distributor must exist in the address book.
     */
    void deleteDistributor(Distributor target);

    /**
     * Adds the given distributor.
     * {@code distributor} must not already exist in the address book.
     */
    void addDistributor(Distributor distributor);
  
    /**
     * Deletes the given product.
     * The product must exist in the address book.
     */
    void deletePerson(Product target);

    /**
     * Adds the given product.
     * {@code product} must not already exist in the address book.
     */
    void addPerson(Product product);

    /**
     * Replaces the given distributor {@code target} with {@code editedDistributor}.
     * {@code target} must exist in the address book.
     * The distributor identity of {@code editedDistributor} must not be the same as another existing distributor in the Inventarie.
     */
    void updateDistributor(Distributor target, Distributor editedDistributor);

    
    /** Returns an unmodifiable view of the filtered person list
     * The product identity of {@code editedProduct} must not be the same as another existing product in the address book.
     */
    void updatePerson(Product target, Product editedProduct);

    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Product> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Distributor> getFilteredDistributorList();

    /**
     * Updates the filter of the filtered distributor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDistributorList(Predicate<Distributor> predicate);

    void updateFilteredPersonList(Predicate<Product> predicate);


    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    boolean checkLoginCredentials(Username username, Password password) throws AuthenticatedException;


}
