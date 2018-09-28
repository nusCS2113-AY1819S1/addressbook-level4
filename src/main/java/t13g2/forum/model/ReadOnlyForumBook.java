package t13g2.forum.model;

import javafx.collections.ObservableList;
import t13g2.forum.model.person.Person;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyForumBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
