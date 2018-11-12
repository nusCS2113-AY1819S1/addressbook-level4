package seedu.divelog.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.divelog.commons.events.model.DiveLogChangedEvent;
import seedu.divelog.commons.events.storage.DataSavingExceptionEvent;
import seedu.divelog.commons.exceptions.DataConversionException;
import seedu.divelog.model.ReadOnlyDiveLog;
import seedu.divelog.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends DiveLogStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getDiveLogFilePath();

    @Override
    Optional<ReadOnlyDiveLog> readDiveLog() throws DataConversionException, IOException;

    @Override
    void saveDiveLog(ReadOnlyDiveLog diveLog) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleDiveLogChangedEvent(DiveLogChangedEvent abce);
}
