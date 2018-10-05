package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.CandidateBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCandidateBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CandidateBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getCandidateBookFilePath();

    @Override
    Optional<ReadOnlyCandidateBook> readCandidateBook() throws DataConversionException, IOException;

    @Override
    void saveCandidateBook(ReadOnlyCandidateBook candidateBook) throws IOException;

    /**
     * Saves the current version of the Candidate Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleCandidateBookChangedEvent(CandidateBookChangedEvent abce);
}
