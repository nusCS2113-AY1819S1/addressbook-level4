package seedu.address.storage;

//@@author QzSG

import java.io.IOException;

import seedu.address.commons.exceptions.OnlineBackupFailureException;
import seedu.address.model.UserPrefs;

/**
 * API of the OnlineStorage component
 */
public interface OnlineStorage {

    /**
     * Enum types for support online storage
     */
    enum Type {
        GITHUB
    }

    /**
     * Saves the given {@code content} to the online storage.
     * @param content cannot be null.
     * @param fileName cannot be null.
     * @throws OnlineBackupFailureException if there was any problem saving to online storage.
     */
    void saveContentToStorage(String content, String fileName) throws IOException, OnlineBackupFailureException;

    /**
     * Saves the given {@code content} to the online storage.
     * @param content cannot be null.
     * @param fileName cannot be null.
     * @param description can be null.
     * @return Object representing the return of a successful online backup, can be a String or a URL
     * @throws OnlineBackupFailureException if there was any problem saving to online storage.
     */
    Object saveContentToStorage(String content, String fileName, String description)
            throws IOException, OnlineBackupFailureException;

    /**
     * Reads the given {@code targetBook} with reference id from online storage and returns contents as a string.
     * @param targetBook {@code UserPrefs.TargetBook} book type of backup content
     * @param ref Reference string used to identity backup content on online service, example would
     *            be gists id from Github
     * @return Contents of backup returned as a string
     * @throws IOException
     */
    String readContentFromStorage(UserPrefs.TargetBook targetBook, String ref) throws IOException;
}
