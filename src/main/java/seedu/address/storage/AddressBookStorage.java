package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.CandidateBook;
import seedu.address.model.ReadOnlyCandidateBook;

/**
 * Represents a storage for {@link CandidateBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns CandidateBook data as a {@link ReadOnlyCandidateBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCandidateBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyCandidateBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCandidateBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyCandidateBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyCandidateBook)
     */
    void saveAddressBook(ReadOnlyCandidateBook addressBook, Path filePath) throws IOException;

}
