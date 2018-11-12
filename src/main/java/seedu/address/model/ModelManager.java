package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.LeaveListChangedEvent;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.NoEmployeeException;
import seedu.address.session.SessionManager;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedLeaveList versionedLeaveList;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Leave> filteredLeave;
    private final SessionManager sessionManager;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyLeaveList leaveList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedLeaveList = new VersionedLeaveList(leaveList);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredLeave = new FilteredList<>(versionedLeaveList.getRequestList());
        sessionManager = SessionManager.getInstance(this);
    }

    public ModelManager() {
        this(new AddressBook(), new LeaveList(), new UserPrefs());
    }

    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        this(addressBook, new LeaveList(), userPrefs);
    }

    public ModelManager(ReadOnlyLeaveList leaveList, UserPrefs userPrefs) {
        this(new AddressBook(), leaveList, userPrefs);
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public void resetData2(ReadOnlyLeaveList newData) {
        versionedLeaveList.resetData(newData);
        indicateLeaveListChanged();
    }


    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyLeaveList getLeaveList() {
        return versionedLeaveList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        sessionManager.resyncPersonsHashMap(this);
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    /** Raises an event to indicate the model has changed */
    private void indicateLeaveListChanged() {
        raise(new LeaveListChangedEvent(versionedLeaveList));
    }

    /**
     * hasPerson returns true if the person has the same NRIC as anyone else in the address book.
     */
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasLeave(Leave leave) {
        requireNonNull(leave);
        return versionedLeaveList.hasRequest(leave);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteLeave(Leave target) {
        versionedLeaveList.removeRequest(target);
        indicateLeaveListChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addLeave(Leave leave) {
        versionedLeaveList.addRequest(leave);
        updateFilteredLeaveList(PREDICATE_SHOW_ALL_LEAVES);
        indicateLeaveListChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void sortEmployee(String field, String order) throws NoEmployeeException {
        versionedAddressBook.sortEmployeeBy(field, order);
        indicateAddressBookChanged();
    }

    @Override
    public void updateLeave(Leave target, Leave editedLeave) {
        requireAllNonNull(target, editedLeave);

        versionedLeaveList.updateRequest(target, editedLeave);
        indicateLeaveListChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Leave> getFilteredLeaveList() {
        return FXCollections.unmodifiableObservableList(filteredLeave);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredLeaveList(Predicate<Leave> predicate) {
        requireNonNull(predicate);
        filteredLeave.setPredicate(predicate);
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public void commitLeaveList() {
        versionedLeaveList.commit();
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
                && filteredPersons.equals(other.filteredPersons);
    }

}
