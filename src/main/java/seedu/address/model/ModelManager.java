package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.UserDatabaseChangedEvent;
import seedu.address.commons.events.model.UserDeletedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.distributor.Distributor;
import seedu.address.model.login.Password;
import seedu.address.model.login.UniqueUsersList;
import seedu.address.model.login.User;
import seedu.address.model.login.Username;
import seedu.address.model.login.exceptions.AuthenticatedException;
import seedu.address.model.login.exceptions.DuplicateUserException;
import seedu.address.model.login.exceptions.UserNotFoundException;
import seedu.address.model.person.Product;
import seedu.address.model.timeidentifiedclass.transaction.Transaction;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.Storage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Storage storage;
    private final VersionedAddressBook versionedAddressBook;
    private final VersionedUserDatabase versionedUserDatabase;

    private final FilteredList<Distributor> filteredDistributors;
    private final FilteredList<Product> filteredProducts;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs,
                        ReadOnlyUserDatabase userDatabase, Storage storage) {
        super();
        requireAllNonNull(addressBook, userPrefs, userDatabase);
        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs
        + " and user database " + userDatabase);
        this.storage = storage;
        versionedUserDatabase = new VersionedUserDatabase(userDatabase);
        versionedAddressBook = new VersionedAddressBook(addressBook);

        filteredDistributors = new FilteredList<>(versionedAddressBook.getDistributorList());
        filteredProducts = new FilteredList<>(versionedAddressBook.getPersonList());
    }

    public ModelManager(Storage storage) {
        this(new AddressBook(), new UserPrefs(), new UserDatabase(), storage);
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getProductInfoBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyAddressBook getDistributorInfoBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasDistributor(Distributor distributor) {
        requireNonNull(distributor);
        return versionedAddressBook.hasDistributor(distributor);
    }

    @Override
    public boolean hasPerson(Product product) {
        requireNonNull(product);
        return versionedAddressBook.hasPerson(product);
    }


    @Override
    public void deleteDistributor(Distributor target) {
        versionedAddressBook.removeDistributor(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deletePerson(Product target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    private void reloadAddressBook(Username username) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook newData;

        storage.update(versionedUserDatabase.getUser(username));
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            newData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            newData = new AddressBook();
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
        } catch (IOException e) {
            newData = new AddressBook();
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
        }
        versionedAddressBook.resetData(newData);
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
    public boolean checkLoginCredentials(Username username, Password password) throws AuthenticatedException {
        boolean result = versionedUserDatabase.checkLoginCredentials(username, password);
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
    public void setUsersList(UniqueUsersList uniqueUserList) {
        versionedUserDatabase.setUniqueUserList(uniqueUserList);
    }

    //=========== Filtered Person List Accessors =============================================================

    public void addDistributor(Distributor distributor) {
        versionedAddressBook.addDistributor(distributor);
        updateFilteredDistributorList(PREDICATE_SHOW_ALL_DISTRIBUTORS);
        indicateAddressBookChanged();
    }

    public void addPerson(Product product) {
        versionedAddressBook.addPerson(product);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    public void updatePerson(Product target, Product editedProduct) {
        requireAllNonNull(target, editedProduct);
        versionedAddressBook.updatePerson(target, editedProduct);
        indicateAddressBookChanged();
    }

    @Override
    public void updateDistributor(Distributor target, Distributor editedDistributor) {
        requireAllNonNull(target, editedDistributor);

        versionedAddressBook.updateDistributor(target, editedDistributor);
        indicateAddressBookChanged();
    }

    //=========== Filtered Product List Accessors =============================================================


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

    @Override
    public ObservableList<Product> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredProducts);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Product> predicate) {
        requireNonNull(predicate);
        filteredProducts.setPredicate(predicate);

    }

    //=========== Undo/Redo =================================================================================

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
        // MERGE CONFLICT HERE, INTEGRATED BOTH
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredDistributors.equals(other.filteredDistributors)
                && filteredProducts.equals(other.filteredProducts);
    }

    @Override
    public String getActiveDayHistory() {
        return versionedAddressBook.getActiveDayHistory();
    }

    @Override
    public String getDaysHistory(String day) {
        return versionedAddressBook.getDaysHistory(day);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        versionedAddressBook.addTransaction(transaction);
    }

    @Override
    public Transaction getLastTransaction() {
        return versionedAddressBook.getLastTransaction();
    }
}


