package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.InventoryListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.LoginInfoManager;
import seedu.address.model.ReadOnlyInventoryList;
import seedu.address.model.UserPrefs;
import seedu.address.storage.logininfo.LoginInfoStorage;

/**
 * API of the Storage component
 */
public interface Storage extends InventoryListStorage, UserPrefsStorage, LoginInfoStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getInventoryListFilePath();

    @Override
    Optional<ReadOnlyInventoryList> readInventoryList() throws DataConversionException, IOException;

    @Override
    void saveInventoryList(ReadOnlyInventoryList inventoryList) throws IOException;

    @Override
    Optional<LoginInfoManager> readLoginInfo() throws DataConversionException, IOException;

    @Override
    void saveLoginInfo(LoginInfoManager userPrefs) throws IOException;
    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    //void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    /**
     * Saves the current version of the Inventory List to the hard disk
     * Creates the data file if it is missing
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */

    void handleInventoryListChangedEvent(InventoryListChangedEvent ilce);
}
