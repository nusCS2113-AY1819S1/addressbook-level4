package com.t13g2.forum.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.t13g2.forum.commons.core.LogsCenter;
import com.t13g2.forum.commons.exceptions.DataConversionException;
import com.t13g2.forum.commons.exceptions.IllegalValueException;
import com.t13g2.forum.commons.util.FileUtil;
import com.t13g2.forum.model.ReadOnlyForumBook;

/**
 * A class to access ForumBook data stored as an xml file on the hard disk.
 */
public class XmlForumBookStorage implements ForumBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlForumBookStorage.class);

    private Path filePath;

    public XmlForumBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getForumBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyForumBook> readForumBook() throws DataConversionException, IOException {
        return readForumBook(filePath);
    }

    /**
     * Similar to {@link #readForumBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyForumBook> readForumBook(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("ForumBook file "  + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableAddressBook xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveForumBook(ReadOnlyForumBook addressBook) throws IOException {
        saveForumBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveForumBook(ReadOnlyForumBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveForumBook(ReadOnlyForumBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableAddressBook(addressBook));
    }

}
