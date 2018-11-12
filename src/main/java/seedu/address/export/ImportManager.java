package seedu.address.export;

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
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;

//@@author jitwei98
// TODO: add interface and headers
/**
 * Manages importing of AddressBook data.
 */
public class ImportManager implements Import {

    private static final Logger logger = LogsCenter.getLogger(ImportManager.class);

    private Path importPath;

    public ImportManager(Path filePath) {
        this.importPath = filePath;
    }

    public Path getImportPath() {
        return importPath;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(importPath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException,
            FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("AddressBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableAddressBook xmlAddressBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            // TODO: how to handle the returned addressbook and addPersons?
            return Optional.of(xmlAddressBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }
}
