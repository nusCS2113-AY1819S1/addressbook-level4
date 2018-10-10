package com.t13g2.forum.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.t13g2.forum.commons.exceptions.DataConversionException;
import com.t13g2.forum.model.ForumBook;
import com.t13g2.forum.model.ReadOnlyForumBook;

/**
 * Represents a storage for {@link ForumBook}.
 */
public interface ForumBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getForumBookFilePath();

    /**
     * Returns ForumBook data as a {@link ReadOnlyForumBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyForumBook> readForumBook() throws DataConversionException, IOException;

    /**
     * @see #getForumBookFilePath()
     */
    Optional<ReadOnlyForumBook> readForumBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyForumBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveForumBook(ReadOnlyForumBook addressBook) throws IOException;

    /**
     * @see #saveForumBook(ReadOnlyForumBook)
     */
    void saveForumBook(ReadOnlyForumBook addressBook, Path filePath) throws IOException;

}
