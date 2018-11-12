package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyBookInventory;

/**
 * A class to access BookInventory data stored as an xml file on the hard disk.
 */
public class XmlBookInventoryStorage implements BookInventoryStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlBookInventoryStorage.class);

    private Path filePath;
    private Path backupFilePath;

    public XmlBookInventoryStorage(Path filePath) {
        this.filePath = filePath;
        backupFilePath = Paths.get(filePath.toString() + ".backup");
    }

    public Path getBookInventoryFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyBookInventory> readBookInventory() throws DataConversionException, IOException {
        return readBookInventory(filePath);
    }

    /**
     * Similar to {@link #readBookInventory()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyBookInventory> readBookInventory(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("BookInventory file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableBookInventory xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveBookInventory(ReadOnlyBookInventory bookInventory) throws IOException {
        saveBookInventory(bookInventory, filePath);
    }

    /**
     * Similar to {@link #saveBookInventory(ReadOnlyBookInventory)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveBookInventory(ReadOnlyBookInventory bookInventory, Path filePath) throws IOException {
        requireNonNull(bookInventory);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableBookInventory(bookInventory));
    }

    public void backupInventoryBook(ReadOnlyBookInventory addressBook) throws IOException {
        saveBookInventory(addressBook, backupFilePath);
    }
}
