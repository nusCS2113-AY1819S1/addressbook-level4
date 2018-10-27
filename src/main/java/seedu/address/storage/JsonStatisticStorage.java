package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.statistic.Statistic;

/**
 * A class to access Statistics stored in the hard disk as a json file
 */
public class JsonStatisticStorage implements StatisticsStorage{
    private Path filePath;

    public JsonStatisticStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getStatisticFilePath() {
        return filePath;
    }

    @Override
    public Optional<Statistic> readStatistic() throws DataConversionException {
        return readStatistic(filePath);
    }

    /**
     * Similar to {@link #readStatistic()}
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws DataConversionException if the file format is not as expected.
     */
    public Optional<Statistic> readStatistic(Path prefsFilePath) throws DataConversionException {
        return JsonUtil.readJsonFile(prefsFilePath, Statistic.class);
    }

    @Override
    public void saveStatistic(Statistic userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }

}
