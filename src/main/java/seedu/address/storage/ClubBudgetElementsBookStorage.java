package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;

import seedu.address.model.ReadOnlyClubBudgetElementsBook;

/**
 * Represents a storage for {@link seedu.address.model.ClubBudgetElementsBook}.
 */
public interface ClubBudgetElementsBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getClubBudgetElementsBookFilePath();

    /**
     * Returns ClubBudgetElementsBook data as a {@link ReadOnlyClubBudgetElementsBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyClubBudgetElementsBook> readClubBudgetElementsBook() throws DataConversionException, IOException;

    /**
     * @see #getClubBudgetElementsBookFilePath()
     */
    Optional<ReadOnlyClubBudgetElementsBook> readClubBudgetElementsBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyClubBudgetElementsBook} to the storage.
     * @param clubBudgetElementsBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook clubBudgetElementsBook) throws IOException;

    /**
     * @see #saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook)
     */
    void saveClubBudgetElementsBook(ReadOnlyClubBudgetElementsBook clubBudgetElementsBook, Path filePath) throws IOException;

}
