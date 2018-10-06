package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CandidateBookChangedEvent;
import seedu.address.commons.events.model.JobBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCandidateBook;
import seedu.address.model.ReadOnlyJobBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of CandidateBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CandidateBookStorage candidateBookStorage;
    private JobBookStorage jobBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CandidateBookStorage candidateBookStorage, JobBookStorage jobBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.jobBookStorage = jobBookStorage;
        this.candidateBookStorage = candidateBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CandidateBook methods ==============================

    @Override
    public Path getCandidateBookFilePath() {
        return candidateBookStorage.getCandidateBookFilePath();
    }

    @Override
    public Optional<ReadOnlyCandidateBook> readCandidateBook() throws DataConversionException, IOException {
        return readCandidateBook(candidateBookStorage.getCandidateBookFilePath());
    }

    @Override
    public Optional<ReadOnlyCandidateBook> readCandidateBook(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read candidatebook data from file: " + filePath);
        return candidateBookStorage.readCandidateBook(filePath);
    }

    @Override
    public void saveCandidateBook(ReadOnlyCandidateBook addressBook) throws IOException {
        saveCandidateBook(addressBook, candidateBookStorage.getCandidateBookFilePath());
    }

    @Override
    public void saveCandidateBook(ReadOnlyCandidateBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to candidatebook data file: " + filePath);
        candidateBookStorage.saveCandidateBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleCandidateBookChangedEvent(CandidateBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event,
                "Local candidatebook changed, saving to file"));
        try {
            saveCandidateBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    // ========================== JobBook methods ==============================

    @Override
    public Path getJobBookFilePath() {
        return jobBookStorage.getJobBookFilePath();
    };

    @Override
    public Optional<ReadOnlyJobBook> readJobBook() throws DataConversionException, IOException {
        return readJobBook(jobBookStorage.getJobBookFilePath());
    };

    @Override
    public Optional<ReadOnlyJobBook> readJobBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read jobbook data from file: " + filePath);
        return jobBookStorage.readJobBook(filePath);
    }

    @Override
    public void saveJobBook(ReadOnlyJobBook jobBook) throws IOException {
        saveJobBook(jobBook, jobBookStorage.getJobBookFilePath());
    }

    @Override
    public void saveJobBook(ReadOnlyJobBook jobBook, Path filePath) throws IOException {
        logger.fine("Attempting to write jobbook to data file: " + filePath);
        jobBookStorage.saveJobBook(jobBook, filePath);
    }



    /**
     * Saves the current version of the Job Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */

    @Override
    @Subscribe
    public void handleJobBookChangedEvent(JobBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local jobbook changed, saving to file"));
        try {
            saveJobBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    };

}
