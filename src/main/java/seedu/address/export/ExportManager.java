package seedu.address.export;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.Person;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;

//@@author jitwei98
/**
 * Manages exporting of AddressBook data.
 */
public class ExportManager implements Export {

    private static final Logger logger = LogsCenter.getLogger(seedu.address.export.ExportManager.class);

    private FilteredList<Person> filteredPersons;
    private Path exportPath;

    public ExportManager(FilteredList<Person> filteredPersons, Path filePath) {
        this.filteredPersons = filteredPersons;
        this.exportPath = filePath;
    }

    public Path getExportPath() {
        return exportPath;
    }

    // TODO: add header in the interface header, refer to XmlAddressBookStorage
    @Override
    public void saveFilteredAddressBook() throws IOException {
        saveFilteredAddressBook(filteredPersons, exportPath);
    }

    /**
     * Similar to {@link #saveFilteredAddressBook()}
     *
     * @param filePath location of the data. Cannot be null
     */
    @Override
    public void saveFilteredAddressBook(FilteredList<Person> filteredPersons, Path filePath) throws IOException {
        requireNonNull(filteredPersons);
        requireNonNull(filePath);

        if (FileUtil.isFileExists(filePath)) {
            logger.fine("File exists. Overwriting output file: " + filePath.toString());
        } else {
            logger.fine("Initializing output file: " + filePath.toString());
            FileUtil.createIfMissing(filePath);
        }
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableAddressBook(filteredPersons));
    }
}
