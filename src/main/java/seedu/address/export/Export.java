package seedu.address.export;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.person.Person;
import seedu.address.model.todo.Todo;

/**
 * The API of the Export component.
 */
public interface Export {
    // TODO: add header in the interface header, refer to XmlAddressBookStorage
    /**
     * Saves the filteredPersons and filteredTodos to the storage.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFilteredAddressBook() throws IOException;

    /**
     * @see #saveFilteredAddressBook()
     */
    void saveFilteredAddressBook(FilteredList<Person> filteredPersons, Path filePath)
            throws IOException;

}
