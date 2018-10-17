package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.RequestListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserPrefs;
import seedu.address.model.request.ReadOnlyRequests;

/**
 * API of the RequestStorage component
 */
public interface RequestStorage extends RequestListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getRequestListFilePath();

    @Override
    Optional<ReadOnlyRequests> readRequestList() throws DataConversionException, IOException;

    @Override
    void saveRequestList(ReadOnlyRequests requestList) throws IOException;

    /**
     * Saves the current version of the BookInventory to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRequestListChangedEvent(RequestListChangedEvent rlce);
}
