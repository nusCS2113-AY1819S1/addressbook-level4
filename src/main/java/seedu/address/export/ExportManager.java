package seedu.address.export;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.Person;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;

//@@author jitwei98
/**
 * Manages exporting of AddressBook data.
 */
public class ExportManager implements Export {

    private static final String MESSAGE_NOTHING_TO_EXPORT = "There is nothing to export!";
    private static final Logger logger = LogsCenter.getLogger(seedu.address.export.ExportManager.class);

    private ObservableList<Person> filteredPersons;
    private Path exportPath;

    public ExportManager(ObservableList<Person> filteredPersons, Path filePath) {
        this.filteredPersons = filteredPersons;
        this.exportPath = filePath;
    }

    public Path getExportPath() {
        return exportPath;
    }


    // TODO: add header in the interface header, refer to XmlAddressBookStorage
    /**
     * Saves the {@code filteredPersons} to the {@code exportPath}.
     * @throws IOException if there was any problem writing to the file.
     * @throws IllegalValueException if the current addressbook is empty.
     */
    @Override
    public void saveFilteredPersons() throws IOException, IllegalValueException {
        saveFilteredPersons(filteredPersons, exportPath);
    }

    /**
     * Similar to {@link #saveFilteredPersons()}
     * @param filteredPersons cannot be null.
     * @param filePath file path of the data. Cannot be null
     */
    @Override
    public void saveFilteredPersons(ObservableList<Person> filteredPersons, Path filePath) throws IOException, IllegalValueException {
        requireNonNull(filteredPersons);
        requireNonNull(filePath);

        if (filteredPersons.size() <= 0) {
            logger.warning("There is nothing to export!");
            throw new IllegalValueException(MESSAGE_NOTHING_TO_EXPORT);
        }

        if (FileUtil.isFileExists(filePath)) {
            logger.fine("File exists. Overwriting output file: " + filePath.toString());
        } else {
            logger.fine("Initializing output file: " + filePath.toString());
            FileUtil.createIfMissing(filePath);
        }
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableAddressBook(filteredPersons));
    }
}
