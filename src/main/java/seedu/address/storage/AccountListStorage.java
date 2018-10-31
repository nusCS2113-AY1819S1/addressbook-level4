package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAccountList;

/**
 * Represents a storage for {@link seedu.address.model.AccountList}.
 */
public interface AccountListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAccountListFilePath();

    /**
     * Returns AccountList data as a {@link ReadOnlyAccountList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAccountList> readAccountList() throws DataConversionException, IOException;

    /**
     * @see #getAccountListFilePath()
     */
    Optional<ReadOnlyAccountList> readAccountList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAccountList} to the storage.
     * @param accountList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccountList(ReadOnlyAccountList accountList) throws IOException;

    /**
     * @see #saveAccountList(ReadOnlyAccountList)
     */
    void saveAccountList(ReadOnlyAccountList accountList, Path filePath) throws IOException;

}
