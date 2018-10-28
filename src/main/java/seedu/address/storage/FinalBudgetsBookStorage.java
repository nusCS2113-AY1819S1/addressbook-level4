package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyFinalBudgetBook;

/**
 * Represents a storage for {@link seedu.address.model.FinalBudgetsBook}.
 */
public interface FinalBudgetsBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFinalBudgetsBookFilePath();

    /**
     * Returns FinalBudgetsBook data as a {@link seedu.address.model.ReadOnlyFinalBudgetBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFinalBudgetBook> readFinalBudgetsBook() throws DataConversionException, IOException;

    /**
     * @see #getFinalBudgetsBookFilePath()
     */
    Optional<ReadOnlyFinalBudgetBook> readFinalBudgetsBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFinalBudgetBook} to the storage.
     * @param finalBudgetBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFinalBudgetsBook(ReadOnlyFinalBudgetBook finalBudgetBook) throws IOException;

    /**
     * @see #saveFinalBudgetsBook(ReadOnlyFinalBudgetBook)
     */
    void saveFinalBudgetsBook(ReadOnlyFinalBudgetBook finalBudgetBook, Path filePath) throws IOException;

}
