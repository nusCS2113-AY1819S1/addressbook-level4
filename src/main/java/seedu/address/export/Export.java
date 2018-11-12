package seedu.address.export;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

/**
 * The API of the Export component.
 */
public interface Export {
    // TODO: add header in the interface header, refer to XmlAddressBookStorage
    /**
     * Saves the filteredPersons to the storage.
     * @throws IOException if there was any problem writing to the file.
     * @throws IllegalValueException if the current addressbook is empty.
     */
    void saveFilteredPersons() throws IOException, IllegalValueException;

    /**
     * @see #saveFilteredPersons()
     */
    void saveFilteredPersons(ObservableList<Person> filteredPersons, Path filePath)
            throws IOException, IllegalValueException;

}
