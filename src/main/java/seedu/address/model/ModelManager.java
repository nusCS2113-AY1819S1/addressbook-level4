package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.DistributorBookChangedEvent;
import seedu.address.commons.events.model.ProductDatabaseChangedEvent;
import seedu.address.commons.events.model.SalesHistoryChangedEvent;
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
import seedu.address.model.saleshistory.ReadOnlySalesHistory;
import seedu.address.model.saleshistory.SalesHistory;
import seedu.address.model.timeidentifiedclass.Reminder;
import seedu.address.model.timeidentifiedclass.Transaction;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateReminderException;
import seedu.address.model.timeidentifiedclass.exceptions.DuplicateTransactionException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.SampleDistributorsUtil;
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
    private final VersionedSalesHistory versionedSalesHistory;

    private final FilteredList<Distributor> filteredDistributors;
    private final FilteredList<Product> filteredProducts;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyProductDatabase addressBook,
                        ReadOnlyDistributorBook distributorBook, UserPrefs userPrefs,
                        ReadOnlyUserDatabase userDatabase, ReadOnlySalesHistory salesHistory, Storage storage) {
        super();
        requireAllNonNull(addressBook, userPrefs, userDatabase);
        logger.fine("Initializing with address book: " + addressBook
                + "and distributor book: " + distributorBook
                + ", user prefs " + userPrefs
                + ", user database " + userDatabase
                + ", and sales history " + salesHistory);

        this.storage = storage;
        versionedUserDatabase = new VersionedUserDatabase(userDatabase);
        versionedDistributorBook = new VersionedDistributorBook(distributorBook);
        versionedAddressBook = new VersionedProductDatabase(addressBook);
        versionedSalesHistory = new VersionedSalesHistory(salesHistory);
        filteredDistributors = new FilteredList<>(versionedDistributorBook.getDistributorList());
        filteredProducts = new FilteredList<>(versionedAddressBook.getProductList());
    }

    /**
     * Initializes a {@code ModelManager} without sales history.
     * Needed as {@code SalesHistory} file is read after login.
     */
    public ModelManager(ReadOnlyProductDatabase addressBook,
                        ReadOnlyDistributorBook distributorBook, UserPrefs userPrefs,
                        ReadOnlyUserDatabase userDatabase, Storage storage) {
        this(addressBook, distributorBook, userPrefs, userDatabase, new SalesHistory(), storage);
    }

    public ModelManager(Storage storage) {
        this(new ProductDatabase(), new DistributorBook(),
                new UserPrefs(), new UserDatabase(),
                new SalesHistory(), storage);
    }

    /**
     * Allows us to set sales history to what is required.
     */
    public void setSalesHistory(ReadOnlySalesHistory salesHistory) {
        versionedSalesHistory.resetData(salesHistory);
    }

    // ============== ProductDatabase Modifiers =============================================================

    @Override
    public ReadOnlyProductDatabase getProductInfoBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new ProductDatabaseChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasProduct(Product product) {
        requireNonNull(product);
        return versionedAddressBook.hasProduct(product);
    }

    @Override
    public boolean hasProductName(String name) {
        requireNonNull(name);
        return versionedAddressBook.hasProductName(name);
    }

    @Override
    public void deleteProduct(Product target) {
        versionedAddressBook.removeProduct(target);
        indicateAddressBookChanged();
    }

    /**
     * Updates the addressBook and its storage path.
     */
    private void reloadAddressBook() {
        Optional<ReadOnlyProductDatabase> addressBookOptional;
        ReadOnlyProductDatabase newAddressBook;

        try {
            addressBookOptional = storage.readProductDatabaseBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample ProductDatabase");
            }
            newAddressBook = addressBookOptional.orElseGet(SampleDataUtil::getSampleProductDatabase);
        } catch (DataConversionException e) {
            newAddressBook = new ProductDatabase();
            logger.warning("Data file not in the correct format. Will be starting with an empty ProductDatabase");
        } catch (IOException e) {
            newAddressBook = new ProductDatabase();
            logger.warning("Problem while reading from the file. Will be starting with an empty ProductDatabase");
        }
        versionedAddressBook.resetData(newAddressBook);
    }

    /**
     * Reloads the sales history
     */
    private void reloadSalesHistory() {
        Optional<ReadOnlySalesHistory> salesHistoryOptional;
        ReadOnlySalesHistory newSalesHistory;

        try {
            salesHistoryOptional = storage.readSalesHistory();
            if (!salesHistoryOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with an empty SalesHistory");
            }
            newSalesHistory = salesHistoryOptional.orElseGet(SampleDataUtil::getSampleSalesHistory);
        } catch (DataConversionException e) {
            newSalesHistory = new SalesHistory();
            logger.warning("Data file not in the correct format. Will be starting with an empty SalesHistory");
        } catch (IOException e) {
            newSalesHistory = new SalesHistory();
            logger.warning("Problem while reading from the file. Will be starting with an empty SalesHistory");
        }

        versionedSalesHistory.resetData(newSalesHistory);
    }

    // ============== DistributorBook Modifiers =============================================================

    @Override
    public void resetData(ReadOnlyProductDatabase newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

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
        return versionedDistributorBook.hasDistributor(distributor);
    }

    @Override
    public boolean hasDistributorName(Distributor distributor) {
        return versionedDistributorBook.hasDistributorName(distributor);
    }

    @Override
    public boolean hasDistributorPhone(Distributor distributor) {
        return versionedDistributorBook.hasDistributorPhone(distributor);
    }

    @Override
    public void deleteDistributor(Distributor target) {
        versionedDistributorBook.removeDistributor(target);
        indicateDistributorBookChanged();
    }

    /**
     * Updates the distributorBook and its storage path.
     */
    private void reloadDistributorBook() {
        Optional<ReadOnlyDistributorBook> distributorBookOptional;
        ReadOnlyDistributorBook newData;

        try {
            distributorBookOptional = storage.readDistributorBook();
            if (!distributorBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample DistributorBook");
            }
            newData = distributorBookOptional.orElseGet(SampleDistributorsUtil::getSampleDistributorBook);
        } catch (DataConversionException e) {
            newData = new DistributorBook();
            logger.warning("Data file not in the correct format. Will be starting with an empty DistributorBook");
        } catch (IOException e) {
            newData = new DistributorBook();
            logger.warning("Problem while reading from the file. Will be starting with an empty DistributorBook");
        }
        versionedDistributorBook.resetData(newData);
    }

    //============== UserDatabase Modifiers =============================================================

    @Override
    public ReadOnlyProductDatabase getUserDatabase() {
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
            storage.update(versionedUserDatabase.getUser(username));
            reloadAddressBook();
            reloadSalesHistory();
            reloadDistributorBook();
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
    public ReadOnlyProductDatabase getAddressBook() {
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
    public void addProduct(Product product) {
        versionedAddressBook.addProduct(product);
        updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateProduct(Product target, Product editedProduct) {
        requireAllNonNull(target, editedProduct);
        versionedAddressBook.updateProducts(target, editedProduct);
        indicateAddressBookChanged();
    }



    //=========== Filtered Distributor List Modifiers =============================================================

    @Override
    public void addDistributor(Distributor distributor) {
        versionedDistributorBook.addDistributor(distributor);
        updateFilteredDistributorList(PREDICATE_SHOW_ALL_DISTRIBUTORS);
        indicateDistributorBookChanged();
    }

    @Override
    public void updateDistributor(Distributor target, Distributor editedDistributor) {
        requireAllNonNull(target, editedDistributor);
        versionedDistributorBook.updateDistributor(target, editedDistributor);
        indicateDistributorBookChanged();
    }

    //=========== Filtered Product List Accessors =================================================================


    @Override
    public ObservableList<Product> getFilteredProductList() {
        return FXCollections.unmodifiableObservableList(filteredProducts);
    }

    @Override
    public void updateFilteredProductList(Predicate<Product> predicate) {
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
    public boolean canUndoProductDatabase() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoProductDatabase() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoProductDatabase() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoProductDatabase() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitProductDatabase() {
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


    //=========================== SalesHistory accessories ===================================

    private void indicateSalesHistoryChanged() {
        raise(new SalesHistoryChangedEvent(versionedSalesHistory));
    }

    @Override
    public void commitSalesHistory() {
        versionedSalesHistory.commit();
    }

    public SalesHistory getSalesHistory() {
        return new SalesHistory(versionedSalesHistory);
    }

    @Override
    public ArrayList<Reminder> getAllReminders() {
        ArrayList<Reminder> reminders = new ArrayList<>();
        for (Reminder toAdd: versionedSalesHistory.getRemindersAsObservableList()) {
            if (toAdd.getReminderTime() != null && !toAdd.getReminderTime().equals("")) {
                reminders.add(toAdd);
            }
        }
        return reminders;
    }

    @Override
    public String getDaysTransactionsAsString(String date) throws InvalidTimeFormatException {
        try {
            return versionedSalesHistory.getDaysTransactionsAsString(date);
        } catch (InvalidTimeFormatException e) {
            throw e;
        }
    }

    @Override
    public String getTransactionAsString(String date) throws InvalidTimeFormatException {
        try {
            return versionedSalesHistory.getTransactionAsString(date);
        } catch (InvalidTimeFormatException e) {
            throw e;
        }
    }

    @Override
    public void addTransaction(Transaction transaction) throws InvalidTimeFormatException,
            DuplicateTransactionException {
        transaction.closeTransaction();
        try {
            versionedSalesHistory.addTransaction(transaction);
        } catch (DuplicateTransactionException e) {
            throw e;
        } catch (InvalidTimeFormatException e) {
            throw e;
        }
        indicateSalesHistoryChanged();
    }

    @Override
    public void addReminder(Reminder reminder) throws InvalidTimeFormatException, DuplicateReminderException {
        try {
            versionedSalesHistory.addReminder(reminder);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (DuplicateReminderException e) {
            throw e;
        }
        commitSalesHistory();
        indicateSalesHistoryChanged();
    }

    @Override
    public void removeReminder(String reminderTime) throws InvalidTimeFormatException, NoSuchElementException {
        try {
            versionedSalesHistory.removeReminder(reminderTime);
        } catch (InvalidTimeFormatException e) {
            throw e;
        } catch (NoSuchElementException e) {
            throw e;
        }
        commitSalesHistory();
        indicateSalesHistoryChanged();
    }

    @Override
    public ArrayList<Reminder> getOverdueReminders() {
        return versionedSalesHistory.getOverdueReminders();
    }

    @Override
    public ArrayList<Reminder> getOverdueRemindersForThread() {
        return versionedSalesHistory.getOverDueRemindersForThread();
    }

    @Override
    public Transaction getLastTransaction() {
        return versionedSalesHistory.getLastTransaction();
    }
}
