package seedu.address.export;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Person;

/**
 * The API of the Export component.
 */
public interface Export {
    // TODO: add header in the interface header, refer to XmlAddressBookStorage
    /**
     * Saves the filteredPersons to the storage.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFilteredPersons() throws IOException;

    /**
     * @see #saveFilteredPersons()
     */
    void saveFilteredPersons(FilteredList<Person> filteredPersons, Path filePath)
            throws IOException;

}
