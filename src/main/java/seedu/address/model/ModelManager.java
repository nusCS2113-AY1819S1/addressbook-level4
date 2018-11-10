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
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.distribute.Distribute;
import seedu.address.model.distribute.DistributeAlgorithm;
import seedu.address.model.group.AddGroup;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Group> filteredGroups;
    private final String scriptFolderLocation;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredGroups = new FilteredList<>(versionedAddressBook.getGroupList());
        scriptFolderLocation = userPrefs.getScriptFileDirectory();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    public VersionedAddressBook getVersionedAddressBook() {
        return versionedAddressBook;
    }

    /**
     * Raises an event to indicate the model has changed
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

    /**
     * Returns true if a group with the same identity fields
     * as {@code group} exists in the versioned address book.
     *
     * @param group Group to check for.
     * @return Check result.
     */
    @Override
    public boolean hasGroup(Group group) {
        requireNonNull(group);
        return versionedAddressBook.hasGroup(group);
    }

    /**
     * Adds a group to the versioned address book.
     * The group must not already exist in the versioned address book.
     *
     * @param group Group to add.
     */
    @Override
    public void createGroup(Group group) {
        requireNonNull(group);
        versionedAddressBook.createGroup(group);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        indicateAddressBookChanged();
    }

    /**
     * Adds persons to a group in the versioned address book.
     * The persons must not already exist in the group.
     * The group must exist in the versioned address book.
     *
     * @param addGroup Contains group and persons to add to group.
     */
    @Override
    public void addGroup(AddGroup addGroup) {
        requireNonNull(addGroup);
        versionedAddressBook.addGroup(addGroup);
        updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        indicateAddressBookChanged();
    }

    /**
     * Return true if a person is already in the specified group.
     *
     * @param addGroup Contains group and person to check with.
     * @return Check result.
     */
    @Override
    public boolean hasPersonInGroup(AddGroup addGroup) {
        requireNonNull(addGroup);
        return versionedAddressBook.hasPersonInGroup(addGroup);
    }

    /**
     * Deletes {@code target} from the versioned address book.
     * {@code target} must exist in the versioned address book.
     *
     * @param target Group to delete.
     */
    @Override
    public void deleteGroup(Group target) {
        versionedAddressBook.removeGroup(target);
        indicateAddressBookChanged();
    }

    /**
     * Removes person from a group.
     *
     * @param group Group to remove person from.
     * @param target Person to be removed.
     */
    @Override
    public void deleteGroupPerson(Group group, Person target) {
        versionedAddressBook.removeGroupPerson(group, target);
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

    //=========== Filtered Group List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Group} backed by the internal list of
     * {@code versionedAddressBook}.
     *
     * @return Unmodifiable list.
     */
    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return FXCollections.unmodifiableObservableList(filteredGroups);
    }

    /**
     * Sets the predicate's value so that an updated group list will be displayed.
     *
     * @param predicate Group predicate to be set.
     */
    @Override
    public void updateFilteredGroupList(Predicate<Group> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
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

    //=========== ScriptCommand Default Folder Directory =====================================================

    @Override
    public String getScriptFolderLocation() {
        return scriptFolderLocation;
    }

    @Override
    public void executeDistributeAlgorithm(Model model, Distribute distribute) throws CommandException {
        new DistributeAlgorithm(model, distribute);
        indicateAddressBookChanged();
    }

    /**
     * Returns true if both objects have the same fields.
     *
     * @param obj Object to compare with.
     * @return Comparison result.
     */
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
                && filteredGroups.equals(other.filteredGroups);
    }

}
