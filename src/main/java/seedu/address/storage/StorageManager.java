package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.CandidateBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyCandidateBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of CandidateBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CandidateBookStorage candidateBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(CandidateBookStorage candidateBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
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
        logger.fine("Attempting to read data from file: " + filePath);
        return candidateBookStorage.readCandidateBook(filePath);
    }

    @Override
    public void saveCandidateBook(ReadOnlyCandidateBook addressBook) throws IOException {
        saveCandidateBook(addressBook, candidateBookStorage.getCandidateBookFilePath());
    }

    @Override
    public void saveCandidateBook(ReadOnlyCandidateBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        candidateBookStorage.saveCandidateBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleCandidateBookChangedEvent(CandidateBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveCandidateBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
