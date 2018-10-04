package com.t13g2.forum.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.t13g2.forum.commons.events.model.AddressBookChangedEvent;
import com.t13g2.forum.commons.events.storage.DataSavingExceptionEvent;
import com.t13g2.forum.commons.exceptions.DataConversionException;
import com.t13g2.forum.model.ReadOnlyForumBook;
import com.t13g2.forum.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ForumBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getForumBookFilePath();

    @Override
    Optional<ReadOnlyForumBook> readForumBook() throws DataConversionException, IOException;

    @Override
    void saveForumBook(ReadOnlyForumBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
