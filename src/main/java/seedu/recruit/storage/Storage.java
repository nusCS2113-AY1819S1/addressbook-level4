package seedu.recruit.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.recruit.commons.events.model.CandidateBookChangedEvent;
import seedu.recruit.commons.events.model.CompanyBookChangedEvent;
import seedu.recruit.commons.events.storage.DataSavingExceptionEvent;
import seedu.recruit.commons.exceptions.DataConversionException;
import seedu.recruit.model.ReadOnlyCandidateBook;
import seedu.recruit.model.ReadOnlyCompanyBook;
import seedu.recruit.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CandidateBookStorage, CompanyBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    // ================ CandidateBook methods ==============================

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
    void handleCandidateBookChangedEvent(CandidateBookChangedEvent event);

    // ================ CompanyBook methods ==============================

    @Override
    Path getCompanyBookFilePath();

    @Override
    Optional<ReadOnlyCompanyBook> readCompanyBook() throws DataConversionException, IOException;

    @Override
    void saveCompanyBook(ReadOnlyCompanyBook companyBook) throws IOException;

    /**
     * Saves the current version of the CompanyBook to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleCompanyBookChangedEvent(CompanyBookChangedEvent event);
}
