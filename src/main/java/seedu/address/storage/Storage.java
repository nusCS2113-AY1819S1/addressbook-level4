package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.events.model.StockListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStockList;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends StockListStorage, UserPrefsStorage {


    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getStockListFilePath();

    @Override
    Optional<ReadOnlyStockList> readStockList() throws DataConversionException, IOException;

    @Override
    void saveStockList(ReadOnlyStockList stockList) throws IOException;

    /**
     * Saves the current version of the Stock List to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleStockListChangedEvent(StockListChangedEvent stockList);
}
