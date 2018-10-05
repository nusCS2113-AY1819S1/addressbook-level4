package seedu.address.storage.machine;

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
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.XmlFileStorage;




/**
 * A class to access makerManagerAddressBook data stored as xml file on the hard disk.
 */
public class XmlMakerManagerMachineStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlMakerManagerMachineStorage.class);

    private Path filePath;

    public XmlMakerManagerMachineStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format
     */
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws FileNotFoundException,
            DataConversionException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Machine file " + filePath + " not found");
            return Optional.empty();
        }

        logger.info("Filepath in XmlMakerManagerMachineStorage class : " + filePath.toString());

        XmlSerializableMakerManagerMachines xmlSerializableMakerManagerMachines =
                XmlFileStorage.loadMakerManagerDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlSerializableMakerManagerMachines.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    /**
     * Should not do anything
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(UserPrefs userPrefs) throws DataConversionException, IOException {
        return Optional.empty();
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveMakerManagerDataToFile(filePath, new XmlSerializableMakerManagerMachines(addressBook));
    }

    /**
     * Should not be used here
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) throws IOException {

    }

    /**
     * Should not be used here
     */

    @Override
    public UserPrefs getUserPrefs() {
        return null;
    }
}
