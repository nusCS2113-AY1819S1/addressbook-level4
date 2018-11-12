package seedu.address.export;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;

//@@author jitwei98
/**
 * The API of the Import component.
 */
public interface Import {

    /**
     * Returns the addressBook from the xml file specified.
     * @throws DataConversionException if the file is not in the correct format.
     * @throws FileNotFoundException if the file does not exist
     */
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     */
    Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, FileNotFoundException;

}
