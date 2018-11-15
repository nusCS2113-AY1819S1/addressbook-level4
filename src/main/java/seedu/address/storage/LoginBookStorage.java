package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyLoginBook;

/**
 * Represents a storage for {@link seedu.address.model.LoginBook}.
 */
public interface LoginBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLoginBookFilePath();

    /**
     * Returns LoginBook data as a {@link ReadOnlyLoginBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLoginBook> readLoginBook() throws DataConversionException, IOException;

    /**
     * @see #getLoginBookFilePath()
     */
    Optional<ReadOnlyLoginBook> readLoginBook(Path loginFilePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLoginBook} to the storage.
     * @param loginBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLoginBook(ReadOnlyLoginBook loginBook) throws IOException;

    /**
     * @see #saveLoginBook(ReadOnlyLoginBook)
     */
    void saveLoginBook(ReadOnlyLoginBook loginBook, Path loginFilePath) throws IOException;
}
