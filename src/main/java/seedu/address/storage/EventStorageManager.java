/*
package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

//import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
//import seedu.address.commons.events.model.EventListChangedEvent;
//import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEventList;
import seedu.address.model.UserPrefs;

*/
/**
 * Manages storage of AddressBook data in local storage.
 *//*

public class EventStorageManager extends ComponentManager implement Storage{

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EventStorage eventStorage;
    private UserPrefsStorage userPrefsStorage;


    public EventStorageManager(EventStorage eventStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.eventStorage = eventStorage;
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


    // ================ EventList methods ==============================

    @Override
    public Path getEventListFilePath() {
        return eventStorage.getEventListFilePath();
    }

    @Override
    public Optional<ReadOnlyEventList> readEventList() throws DataConversionException, IOException {
        return readEventList(eventStorage.getEventListFilePath());
    }

    @Override
    public Optional<ReadOnlyEventList> readEventList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventStorage.readEventList(filePath);
    }

    @Override
    public void saveEventList(ReadOnlyEventList eventList) throws IOException {
        saveEventList(eventList, eventStorage.getEventListFilePath());
    }

    @Override
    public void saveEventList(ReadOnlyEventList eventList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventStorage.saveEventList(eventList, filePath);
    }


    @Override
    @Subscribe
    public void handleEventListChangedEvent(EventListChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEventList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
*/

