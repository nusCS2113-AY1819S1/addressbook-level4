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
import seedu.address.commons.events.model.EventListChangedEvent;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;

    private final VersionedEventList versionedEventList;
    private final FilteredList<Event> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyEventList eventList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, eventList, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());

        versionedEventList = new VersionedEventList(eventList);
        filteredEvents = new FilteredList<>(versionedEventList.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new EventList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData, ReadOnlyEventList newEventList) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
        indicateEventListChanged();
    }

    //=========== Address Book Methods =======================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    /**
     * Raises an event to indicate the addressbook model has changed
     */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
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

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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

    //=========== EventList Methods ==========================================================================

    @Override
    public ReadOnlyEventList getEventList() {
        return versionedEventList;
    }

    /**
     * Raises an event to indicate the eventlist model has changed
     */
    private void indicateEventListChanged() {
        raise(new EventListChangedEvent(versionedEventList));
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return versionedEventList.hasEvent(event);
    }

    @Override
    public void deleteEvent(Event target) {
        versionedEventList.removeEvent(target);
        indicateEventListChanged();
    }

    @Override
    public void addEvent(Event event) {
        versionedEventList.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventListChanged();
    }

    @Override
    public void updateEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        versionedEventList.updateEvent(target, editedEvent);
        indicateEventListChanged();
    }

    @Override
    public ObservableList<Event> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<Event> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public void sortByName() {
        versionedEventList.sortByName();
    }

    @Override
    public void sortByStartTime() {
        versionedEventList.sortByStartTime();
    }

    @Override
    public void sortByEndTime() {
        versionedEventList.sortByEndTime();
    }

    @Override
    public void sortByDate() {
        versionedEventList.sortByDate();
    }


    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoEventList() {
        return versionedEventList.canUndo();
    }

    @Override
    public boolean canRedoEventList() {
        return versionedEventList.canRedo();
    }

    @Override
    public void undoEventList() {
        versionedEventList.undo();
        indicateEventListChanged();
    }

    @Override
    public void redoEventList() {
        versionedEventList.redo();
        indicateEventListChanged();
    }

    @Override
    public void commitEventList() {
        versionedEventList.commit();
    }

    //=========== Object Methods =============================================================================

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
                && filteredPersons.equals(other.filteredPersons)
                && versionedEventList.equals(other.versionedEventList)
                && filteredEvents.equals(other.filteredEvents);
    }
}
