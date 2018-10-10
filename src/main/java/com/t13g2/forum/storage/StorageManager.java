package com.t13g2.forum.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;
import com.t13g2.forum.commons.core.ComponentManager;
import com.t13g2.forum.commons.core.LogsCenter;
import com.t13g2.forum.commons.events.model.AddressBookChangedEvent;
import com.t13g2.forum.commons.events.storage.DataSavingExceptionEvent;
import com.t13g2.forum.commons.exceptions.DataConversionException;
import com.t13g2.forum.model.ReadOnlyForumBook;
import com.t13g2.forum.model.UserPrefs;

/**
 * Manages storage of ForumBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ForumBookStorage forumBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ForumBookStorage forumBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.forumBookStorage = forumBookStorage;
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


    // ================ ForumBook methods ==============================

    @Override
    public Path getForumBookFilePath() {
        return forumBookStorage.getForumBookFilePath();
    }

    @Override
    public Optional<ReadOnlyForumBook> readForumBook() throws DataConversionException, IOException {
        return readForumBook(forumBookStorage.getForumBookFilePath());
    }

    @Override
    public Optional<ReadOnlyForumBook> readForumBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return forumBookStorage.readForumBook(filePath);
    }

    @Override
    public void saveForumBook(ReadOnlyForumBook addressBook) throws IOException {
        saveForumBook(addressBook, forumBookStorage.getForumBookFilePath());
    }

    @Override
    public void saveForumBook(ReadOnlyForumBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        forumBookStorage.saveForumBook(addressBook, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveForumBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
