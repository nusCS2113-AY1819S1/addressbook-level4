package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyStockList;

/**
 * A class to access StockList data stored as an xml file on the hard disk.
 */
public class XmlStockListStorage implements StockListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlStockListStorage.class);

    private final Path filePath;

    /**
     * Reused from https://github.com/se-edu/addressbook-level4 solutions
     * @param filePath
     */
    public XmlStockListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getStockListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyStockList> readStockList() throws DataConversionException, IOException {
        return readStockList(filePath);
    }

    /**
     * Similar to {@link #readStockList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyStockList> readStockList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("StockList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableStockList xmlStockList = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlStockList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveStockList(ReadOnlyStockList stockList) throws IOException {
        saveStockList(stockList, filePath);
    }

    /**
     * Similar to {@link #saveStockList(ReadOnlyStockList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveStockList(ReadOnlyStockList stockList, Path filePath) throws IOException {
        requireNonNull(stockList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableStockList(stockList));
    }
    //@@author kelvintankaiboon
    @Override
    public void saveStockListVersion(ReadOnlyStockList stockList, Path filePath) throws IOException {
        requireNonNull(stockList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableStockList(stockList));
    }
}
