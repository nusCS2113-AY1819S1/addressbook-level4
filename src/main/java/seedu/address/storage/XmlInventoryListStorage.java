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
import seedu.address.model.ReadOnlyInventoryList;


/**
 * A class to access InventoryList data stored as an xml file on the hard disk.
 */
public class XmlInventoryListStorage implements InventoryListStorage {
    private static final Logger logger = LogsCenter.getLogger(XmlInventoryListStorage.class);

    private Path filePath;

    public XmlInventoryListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getInventoryListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyInventoryList> readInventoryList() throws DataConversionException, IOException {
        return readInventoryList(filePath);
    }

    /**
     * Similar to {@link #readInventoryList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyInventoryList> readInventoryList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Inventory List file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableInventoryList xmlInventoryList = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlInventoryList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveInventoryList(ReadOnlyInventoryList inventoryList) throws IOException {
        saveInventoryList(inventoryList, filePath);
    }

    /**
     * Similar to {@link #saveInventoryList(ReadOnlyInventoryList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveInventoryList(ReadOnlyInventoryList inventoryList, Path filePath) throws IOException {
        requireNonNull(inventoryList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableInventoryList(inventoryList));
    }
}
