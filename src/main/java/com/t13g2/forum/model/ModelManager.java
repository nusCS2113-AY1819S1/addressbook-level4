package com.t13g2.forum.model;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import com.t13g2.forum.commons.core.ComponentManager;
import com.t13g2.forum.commons.core.LogsCenter;
import com.t13g2.forum.commons.events.model.AddressBookChangedEvent;
import com.t13g2.forum.commons.events.model.ShowAnnouncementEvent;
import com.t13g2.forum.commons.events.model.UserLoginEvent;
import com.t13g2.forum.commons.util.CollectionUtil;
import com.t13g2.forum.model.forum.Announcement;
import com.t13g2.forum.model.forum.User;
import com.t13g2.forum.model.person.Person;
import com.t13g2.forum.storage.forum.Context;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedForumBook versionedAddressBook;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyForumBook addressBook, UserPrefs userPrefs) {
        super();
        CollectionUtil.requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedForumBook(addressBook);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
    }

    public ModelManager() {
        this(new ForumBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyForumBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyForumBook getForumBook() {
        return versionedAddressBook;
    }

    /** Raises an event to indicate the model has changed */
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
        CollectionUtil.requireAllNonNull(target, editedPerson);

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
    public boolean canUndoForumBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoForumBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoForumBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoForumBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitForumBook() {
        versionedAddressBook.commit();
    }

    //@@xllx1
    @Override
    public boolean userLogin(String userName, String userPassword) {
        User user = versionedAddressBook.userLogin(userName, userPassword);
        if (user != null) {
            raise(new UserLoginEvent(userName, user.isAdmin()));
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIsLogin() {
        if (Context.getInstance().getCurrentUser() == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkIsAdmin() {
        if (!Context.getInstance().getCurrentUser().isAdmin()) {
            return false;
        }
        return true;
    }

    @Override
    public void addAnnouncement(Announcement toAnnounce) {
        versionedAddressBook.addAnnouncement(toAnnounce);
        raise(new ShowAnnouncementEvent(toAnnounce.getTitle(), toAnnounce.getContent()));
    }

    @Override
    public Announcement showLatestAnnouncement() {
        Announcement latestAnnouncement = versionedAddressBook.checkAnnounce();
        if (latestAnnouncement != null) {
            raise(new ShowAnnouncementEvent(latestAnnouncement.getTitle(), latestAnnouncement.getContent()));
        }
        return latestAnnouncement;
    }

    @Override
    public User doesUserExist(String userName) {
        return versionedAddressBook.doesUserExist(userName);
    }

    @Override
    public boolean blockUser(User user) {
        return versionedAddressBook.blockUser(user);
    }

    @Override
    public void setAdmin(User user) {
        versionedAddressBook.setAdmin(user);
    }

    //@@author
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
