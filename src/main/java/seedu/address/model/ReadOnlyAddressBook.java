package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons and groups list.
     * This list will not contain any duplicate persons or groups.
     */
    ObservableList<Person> getPersonList();

    ObservableList<Group> getGroupList();

}
