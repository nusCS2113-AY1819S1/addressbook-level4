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
import seedu.address.model.ReadOnlyEventList;

//@@author: IcedCoffeeBoy
/**
 * A class to access EventList data stored as an xml file on the hard disk.
 */
public class XmlEventStorage implements EventStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlEventStorage.class);

    private Path filePath;

    public XmlEventStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getEventListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyEventList> readEventList() throws DataConversionException, IOException {
        return readEventList(filePath);
    }

    /**
     * Similar to {@link #readEventList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyEventList> readEventList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("AddressBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableEventList xmlEventList = XmlFileStorage.loadEventDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlEventList.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveEventList(ReadOnlyEventList eventList) throws IOException {
        saveEventList(eventList, filePath);
    }

    /**
     * Similar to {@link #saveEventList(ReadOnlyEventList)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveEventList(ReadOnlyEventList eventList, Path filePath) throws IOException {
        requireNonNull(eventList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableEventList(eventList));
    }
}

