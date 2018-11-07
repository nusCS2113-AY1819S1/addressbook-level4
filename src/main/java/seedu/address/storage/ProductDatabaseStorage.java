package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ProductDatabase;
import seedu.address.model.ReadOnlyProductDatabase;
import seedu.address.model.login.User;

/**
 * Represents a storage for {@link ProductDatabase}.
 */
public interface ProductDatabaseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getProductInfoBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyProductDatabase}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyProductDatabase> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getProductInfoBookFilePath()
     */
    Optional<ReadOnlyProductDatabase> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyProductDatabase} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyProductDatabase addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyProductDatabase)
     */
    void saveAddressBook(ReadOnlyProductDatabase addressBook, Path filePath) throws IOException;

    void deleteAddressBook(User user) throws IOException;
}
