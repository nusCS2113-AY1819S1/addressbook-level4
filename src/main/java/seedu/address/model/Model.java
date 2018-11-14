package seedu.address.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUserList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.product.Product;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */

    Predicate<Distributor> PREDICATE_SHOW_ALL_DISTRIBUTORS = unused -> true;

    Predicate<Product> PREDICATE_SHOW_ALL_PRODUCTS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyProductDatabase newData);

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyDistributorBook newData);

    /** Returns the Product database */
    ReadOnlyProductDatabase getProductInfoBook();

    /** Returns the DistributorBook */
    ReadOnlyDistributorBook getDistributorInfoBook();
    /**
     * Returns true if a distributor with the same identity as {@code distributor} exists in the Inventarie.
     */
    boolean hasDistributor(Distributor distributor);

    /**
     * Returns true if a distributor with the same name as {@code distributor} exists in the Inventarie.
     */
    boolean hasDistributorName(Distributor distributor);

    /**
     * Returns true if a distributor with the same phone as {@code distributor} exists in the Inventarie.
     */
    boolean hasDistributorPhone(Distributor distributor);

    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    boolean hasProduct(Product product);


    boolean hasProductName(String name);


    /**
     * Deletes the given distributor.
     * The distributor must exist in the address book.
     */
    void deleteDistributor(Distributor target);

    /**
     * Deletes the given product.
     * The product must exist in the address book.
     */
    void deleteProduct(Product target);

    /**
     * Adds the given distributor.
     * {@code distributor} must not already exist in the address book.
     */
    void addDistributor(Distributor distributor);

    /**
     * Adds the given product.
     * {@code product} must not already exist in the address book.
     */
    void addProduct(Product product);

    /**
     * Replaces the given distributor {@code target} with {@code editedDistributor}.
     * {@code target} must exist in the address book.
     * The distributor identity of {@code editedDistributor} must not be the same as another existing distributor
     * in the Inventarie.
     */
    void updateDistributor(Distributor target, Distributor editedDistributor);


    /** Returns an unmodifiable view of the filtered product list
     * The product identity of {@code editedProduct} must not be the same as another existing
     * product in the address book.
     */
    void updateProduct(Product target, Product editedProduct);


    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Product> getFilteredProductList();

    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Distributor> getFilteredDistributorList();

    /**
     * Updates the filter of the filtered distributor list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDistributorList(Predicate<Distributor> predicate);

    void updateFilteredProductList(Predicate<Product> predicate);


    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoProductDatabase();

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoDistributorBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoProductDatabase();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoDistributorBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoProductDatabase();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoDistributorBook();


    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoProductDatabase();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoDistributorBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitProductDatabase();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitDistributorBook();

    /**
     * Adds a transaction to the active shop day.
     * @param transaction
     * @throws InvalidTimeFormatException
     * @throws DuplicateTransactionException
     */
    void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            DuplicateTransactionException;

    /**
     * Adds a reminder to the active business day.
     * @param reminder
     * @throws InvalidTimeFormatException
     * @throws DuplicateReminderException
     */
    void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException;

    /**
     * Removes a reminder from the active business day.
     * @param reminderTime
     * @throws InvalidTimeFormatException
     * @throws NoSuchElementException
     */
    void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException;

    /**
     * Returns ALL the reminders
     */
    ArrayList<Reminder> getAllReminders();

    /**
     * Returns the reminders due on the current active day.
     */
    ArrayList<Reminder> getOverdueReminders();

    /**
     * Returns the reminders that have not been shown by the thread.
     */
    ArrayList<Reminder> getOverdueRemindersForThread();

    /**
     * Returns a given day's transaction history
     */
    String getDaysTransactionsAsString(String date) throws InvalidTimeFormatException;

    /**
     * Finds and returns a transaction
     */
    String getTransactionAsString(String date) throws InvalidTimeFormatException;

    /**
     * Returns the latest transaction.
     */
    Transaction getLastTransaction();

    /**
     * Saves the newest SalesHistory.
     */
    void commitSalesHistory();

    /**
    * Sets the user list
    */
    void setUsersList(UniqueUserList uniqueUserList);

    /** Returns the UserDatabase */
    ReadOnlyProductDatabase getUserDatabase();

    /**
     * Deletes the given user.
     * The user must exist in the user database.
     */
    void deleteUser(User target) throws UserNotFoundException;

    /**
     * Adds the given user.
     * {@code user} must not already exist in the user database.
     */
    void addUser(User person) throws DuplicateUserException;

    boolean checkAuthentication(Username username, Password password) throws AuthenticatedException;

    boolean checkCredentials(Username username, Password password) throws AuthenticatedException;

    boolean hasLoggedIn();

    void setLoginStatus(boolean status);

    User getLoggedInUser();

    void updateUserPassword(User target, User userWithNewPassword) throws UserNotFoundException;

    /** Returns the ProductDatabase */
    ReadOnlyProductDatabase getAddressBook();

    /** Returns the DistributorBook */
    ReadOnlyDistributorBook getDistributorBook();
}
