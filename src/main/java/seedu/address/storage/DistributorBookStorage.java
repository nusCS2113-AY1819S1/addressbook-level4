package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDistributorBook;
import seedu.address.model.login.User;

/**
 * Represents a storage for {@link seedu.address.model.DistributorBook}.
 */
public interface DistributorBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDistributorBookFilePath();

    /**
     * Returns DistributorBook data as a {@link ReadOnlyDistributorBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDistributorBook> readDistributorBook() throws DataConversionException, IOException;

    /**
     * @see #getDistributorBookFilePath()
     */
    Optional<ReadOnlyDistributorBook> readDistributorBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDistributorBook} to the storage.
     * @param distributorBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDistributorBook(ReadOnlyDistributorBook distributorBook) throws IOException;

    /**
     * @see #saveDistributorBook(ReadOnlyDistributorBook)
     */
    void saveDistributorBook(ReadOnlyDistributorBook distributorBook, Path filePath) throws IOException;

    void deleteDistributorBook(User user) throws IOException;
}
