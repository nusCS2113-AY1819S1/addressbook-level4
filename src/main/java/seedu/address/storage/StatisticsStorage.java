package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.statistic.Statistic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public interface StatisticsStorage {
    /**
     * Returns the file path of the Statistic data file.
     */
    Path getStatisticFilePath();

    /**
     * Returns Statistic data from storage.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<Statistic> readStatistic() throws DataConversionException, IOException;

    /**
     * Saves the given {@link seedu.address.model.statistic.Statistic} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStatistic(Statistic userPrefs) throws IOException;

}
