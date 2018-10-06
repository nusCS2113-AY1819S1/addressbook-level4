package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.JobBook;
import seedu.address.model.ReadOnlyJobBook;

/**
 * Represents a storage for {@link JobBook}.
 */
public interface JobBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getJobBookFilePath();

    /**
     * Returns JobBook data as a {@link ReadOnlyJobBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyJobBook> readJobBook() throws DataConversionException, IOException;

    /**
     * @see #getJobBookFilePath()
     */
    Optional<ReadOnlyJobBook> readJobBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyJobBook} to the storage.
     * @param jobBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveJobBook(ReadOnlyJobBook jobBook) throws IOException;

    /**
     * @see #saveJobBook(ReadOnlyJobBook)
     */
    void saveJobBook(ReadOnlyJobBook jobBook, Path filePath) throws IOException;

}
