package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.request.ReadOnlyRequests;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link RequestList}.
 */
public interface RequestListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns RequestList data as a {@link ReadOnlyRequests}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRequests> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyRequests> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRequests} to the storage.
     * @param requestList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRequestList(ReadOnlyRequests requestList) throws IOException;

    /**
     * @see #saveRequestList(ReadOnlyRequests)
     */
    void saveRequestList(ReadOnlyRequests requestList, Path filePath) throws IOException;

}
