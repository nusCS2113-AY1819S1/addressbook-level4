package seedu.divelog.model;

import javafx.collections.ObservableList;
import seedu.divelog.model.person.Person;

/**
 * Unmodifiable view of an divelog book
 */
public interface ReadOnlyDiveLog {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

}
