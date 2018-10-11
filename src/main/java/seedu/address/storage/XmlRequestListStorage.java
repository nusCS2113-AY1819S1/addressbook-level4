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
import seedu.address.model.request.ReadOnlyRequests;

/**
 * A class to access RequestList data stored as an xml file on the hard disk.
 */
public class XmlRequestListStorage implements RequestListStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlRequestListStorage.class);

    private Path filePath;

    public XmlRequestListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRequestListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRequests> readRequestList() throws DataConversionException, IOException {
        return readRequestList(filePath);
    }

    /**
     * Similar to {@link #readRequestList()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRequests> readRequestList(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("RequestList file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableRequestList xmlAddressBook = XmlFileStorage.loadRequestsFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRequestList(ReadOnlyRequests requestList) throws IOException {
        saveRequestList(requestList, filePath);
    }

    /**
     * Similar to {@link #saveRequestList(ReadOnlyRequests)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveRequestList(ReadOnlyRequests requestList, Path filePath) throws IOException {
        requireNonNull(requestList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveRequestToFile(filePath, new XmlSerializableRequestList(requestList));
    }

}
