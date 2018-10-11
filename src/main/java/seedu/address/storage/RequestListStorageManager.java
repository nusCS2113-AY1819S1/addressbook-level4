package seedu.address.storage;

import com.google.common.eventbus.Subscribe;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.RequestListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserPrefs;
import seedu.address.model.request.ReadOnlyRequests;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Manages storage of RequestList data in local storage.
 */
public class RequestListStorageManager extends ComponentManager implements RequestStorage {

    private static final Logger logger = LogsCenter.getLogger(RequestListStorageManager.class);
    private RequestListStorage requestListStorage;
    private UserPrefsStorage userPrefsStorage;


    public RequestListStorageManager(RequestListStorage requestListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.requestListStorage = requestListStorage;
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


    // ================ RequestList methods ==============================

    @Override
    public Path getRequestListFilePath() {
        return requestListStorage.getRequestListFilePath();
    }

    @Override
    public Optional<ReadOnlyRequests> readRequestList() throws DataConversionException, IOException {
        return readRequestList(requestListStorage.getRequestListFilePath());
    }

    @Override
    public Optional<ReadOnlyRequests> readRequestList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return requestListStorage.readRequestList(filePath);
    }

    @Override
    public void saveRequestList(ReadOnlyRequests addressBook) throws IOException {
        saveRequestList(addressBook, requestListStorage.getRequestListFilePath());
    }

    @Override
    public void saveRequestList(ReadOnlyRequests requestList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        requestListStorage.saveRequestList(requestList, filePath);
    }


    @Override
    @Subscribe
    public void handleRequestListChangedEvent(RequestListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveRequestList(event.dataRequest);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
}
