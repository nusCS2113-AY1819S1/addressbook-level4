package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.DistributorBookChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUserList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.product.Product;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.ClosedShopDayException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.shopday.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Storage storage;

    private final VersionedDistributorBook versionedDistributorBook;
    private final VersionedProductDatabase versionedAddressBook;
    private final VersionedUserDatabase versionedUserDatabase;

    private final FilteredList<Distributor> filteredDistributors;
    private final FilteredList<Product> filteredProducts;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyDistributorBook distributorBook, UserPrefs userPrefs,
                        ReadOnlyUserDatabase userDatabase, Storage storage) {
        super();
        requireAllNonNull(addressBook, userPrefs, userDatabase);
        logger.fine("Initializing with address book: " + addressBook
                + "and distributor book: " + distributorBook
                + " and user prefs " + userPrefs
                + " and user database " + userDatabase);
        this.storage = storage;
        versionedUserDatabase = new VersionedUserDatabase(userDatabase);
        versionedDistributorBook = new VersionedDistributorBook(distributorBook);
        versionedAddressBook = new VersionedProductDatabase(addressBook);


        filteredDistributors = new FilteredList<>(versionedDistributorBook.getDistributorList());
        filteredProducts = new FilteredList<>(versionedAddressBook.getPersonList());
    }

    public ModelManager(Storage storage) {
        this(new ProductDatabase(), new DistributorBook(), new UserPrefs(), new UserDatabase(), storage);
    }

    // ============== ProductDatabase Modifiers =============================================================

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getProductInfoBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Product product) {
        requireNonNull(product);
        return versionedAddressBook.hasPerson(product);
    }

    @Override
    public void deletePerson(Product target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    /**
     * Updates the addressBook and its storage path using the {@code username} provided.
     * @param username
     */
    private void reloadAddressBook(Username username) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook newData;

        storage.update(versionedUserDatabase.getUser(username));
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ProductDatabase");
            }
            newData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            newData = new ProductDatabase();
            logger.warning("Data file not in the correct format. Will be starting with an empty ProductDatabase");
        } catch (IOException e) {
            newData = new ProductDatabase();
            logger.warning("Problem while reading from the file. Will be starting with an empty ProductDatabase");
        }
        versionedAddressBook.resetData(newData);
    }

    // ============== DistributorBook Modifiers =============================================================

    @Override
    public void resetData(ReadOnlyDistributorBook newData) {
        versionedDistributorBook.resetData(newData);
        indicateDistributorBookChanged();
    }

    @Override
    public ReadOnlyDistributorBook getDistributorInfoBook() {
        return versionedDistributorBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateDistributorBookChanged() {
        raise(new DistributorBookChangedEvent(versionedDistributorBook));
    }

    @Override
    public boolean hasDistributor(Distributor distributor) {
        requireNonNull(distributor);
        return versionedDistributorBook.hasDistributor(distributor);
    }

    @Override
    public void deleteDistributor(Distributor target) {
        versionedDistributorBook.removeDistributor(target);
        indicateAddressBookChanged();
    }

    /**
     * Updates the distributorBook and its storage path using the {@code username} provided.
     * @param username
     */
    private void reloadDistributorBook(Username username) {
        Optional<ReadOnlyDistributorBook> distributorBookOptional;
        ReadOnlyDistributorBook newData;

        storage.update(versionedUserDatabase.getUser(username));
        try {
            distributorBookOptional = storage.readDistributorBook();
            if (!distributorBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            newData = distributorBookOptional.orElseGet(SampleDataUtil::getSampleDistributorBook);
        } catch (DataConversionException e) {
            newData = new DistributorBook();
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
        } catch (IOException e) {
            newData = new DistributorBook();
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
        }
        versionedDistributorBook.resetData(newData);
    }

    //============== UserDatabase Modifiers =============================================================

    @Override
    public ReadOnlyAddressBook getUserDatabase() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateUserDatabaseChanged() {
        raise(new UserDatabaseChangedEvent(versionedUserDatabase));
    }

    /** Raises an event to indicate a user has been deleted */
    private void indicateUserDeleted(User user) {
        raise(new UserDeletedEvent(user));
    }


    @Override
    public synchronized void deleteUser(User target) throws UserNotFoundException {
        versionedUserDatabase.removeUser(target);
        indicateUserDatabaseChanged();
        indicateUserDeleted(target);
    }

    @Override
    public synchronized void addUser(User person) throws DuplicateUserException {
        versionedUserDatabase.addUser(person);
        indicateUserDatabaseChanged();
    }

    @Override
    public boolean checkAuthentication(Username username, Password password) throws AuthenticatedException {
        boolean result = versionedUserDatabase.checkAuthentication(username, password);
        if (hasLoggedIn() && result) {
            reloadAddressBook(username);
        }
        return result;
    }

    @Override
    public boolean checkCredentials(Username username, Password password) throws AuthenticatedException {
        return versionedUserDatabase.checkCredentials(username, password);
    }

    @Override
    public void updateUserPassword(User target, User userWithNewPassword)
            throws UserNotFoundException {
        requireAllNonNull(target, userWithNewPassword);
        versionedUserDatabase.updateUserPassword(target, userWithNewPassword);
        indicateUserDatabaseChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return null;
    }

    @Override
    public ReadOnlyDistributorBook getDistributorBook() {
        return null;
    }

    @Override
    public User getLoggedInUser() {
        return versionedUserDatabase.getLoggedInUser();
    }


    @Override
    public boolean hasLoggedIn() {
        return versionedUserDatabase.hasLoggedIn();
    }

    @Override
    public void setLoginStatus(boolean status) {
        versionedUserDatabase.setLoginStatus(status);
    }

    @Override
    public void setUsersList(UniqueUserList uniqueUserList) {
        versionedUserDatabase.setUniqueUserList(uniqueUserList);
    }

    //=========== Filtered Product List Modifiers =================================================================

    @Override
    public void addPerson(Product product) {
        versionedAddressBook.addPerson(product);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Product target, Product editedProduct) {
        requireAllNonNull(target, editedProduct);
        versionedAddressBook.updatePerson(target, editedProduct);
        indicateAddressBookChanged();
    }

    //=========== Filtered Distributor List Modifiers =============================================================

    @Override
    public void addDistributor(Distributor distributor) {
        versionedDistributorBook.addDistributor(distributor);
        updateFilteredDistributorList(PREDICATE_SHOW_ALL_DISTRIBUTORS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateDistributor(Distributor target, Distributor editedDistributor) {
        requireAllNonNull(target, editedDistributor);

        versionedDistributorBook.updateDistributor(target, editedDistributor);
        indicateAddressBookChanged();
    }

    //=========== Filtered Product List Accessors =================================================================


    @Override
    public ObservableList<Product> getFilteredProductList() {
        return FXCollections.unmodifiableObservableList(filteredProducts);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Product> predicate) {
        requireNonNull(predicate);
        filteredProducts.setPredicate(predicate);
    }

    //=========== Filtered Distributor List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Product} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Distributor> getFilteredDistributorList() {
        return FXCollections.unmodifiableObservableList(filteredDistributors);
    }

    @Override
    public void updateFilteredDistributorList(Predicate<Distributor> predicate) {
        requireNonNull(predicate);
        filteredDistributors.setPredicate(predicate);
    }

    //=========== Undo/Redo AB =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
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
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredDistributors.equals(other.filteredDistributors)
                && filteredProducts.equals(other.filteredProducts);
    }

    //=========== Undo/Redo DB =================================================================================

    @Override
    public boolean canUndoDistributorBook() {
        return versionedDistributorBook.canUndo();
    }

    @Override
    public boolean canRedoDistributorBook() {
        return versionedDistributorBook.canRedo();
    }

    @Override
    public void undoDistributorBook() {
        versionedDistributorBook.undo();
        indicateDistributorBookChanged();
    }

    @Override
    public void redoDistributorBook() {
        versionedDistributorBook.redo();
        indicateDistributorBookChanged();
    }

    @Override
    public void commitDistributorBook() {
        versionedDistributorBook.commit();
    }


    //=========== Transactions =================================================================================

    @Override
    public String getActiveDayHistory() {
        return versionedAddressBook.getActiveDayHistory();
    }

    @Override
    public String getDaysHistory(String day) {
        return versionedAddressBook.getDaysHistory(day);
    }

    @Override
    public void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            ClosedShopDayException, DuplicateTransactionException {
        try {
            versionedAddressBook.addTransaction(transaction);
        } catch (DuplicateTransactionException e) {
            throw e;
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (ClosedShopDayException e) {
            throw e;
        }
    }

    @Override
    public void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException {
        if (!Transaction.isValidTransactionTime(reminder.getTime())) {
            throw new InvalidTimeFormatException ();
        }
        try {
            versionedAddressBook.addReminderToActiveShopDay(reminder);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (DuplicateReminderException e) {
            throw e;
        }
    }

    @Override
    public ArrayList<Reminder> getDueRemindersInActiveShopDay() {
        return versionedAddressBook.getDueRemindersInActiveDay();
    }

    @Override
    public Transaction getLastTransaction() {
        return versionedAddressBook.getLastTransaction();
    }
}
