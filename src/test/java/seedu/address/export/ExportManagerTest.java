package seedu.address.export;

import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author jitwei98
public class ExportManagerTest {

    private static final Path EXPORT_FILE_PATH = Paths.get("src", "test", "data", "sandbox", "testImportCommand.xml");

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager;
    private ObservableList<Person> filteredPersons;

    private ExportManager exportManager;

    @Before
    public void setUp() {
        modelManager = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        filteredPersons = modelManager.getFilteredPersonList();

        exportManager = new ExportManager(filteredPersons, EXPORT_FILE_PATH);
    }

    @Test
    public void saveFilteredPersons() throws AssertionError {
        try {
            exportManager.saveFilteredPersons();
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        } catch (IllegalValueException ive) {
            throw new AssertionError("The persons list passed in should not be empty");
        }
    }

    @Test
    public void saveFilteredPersons_nullFilteredPersons_throwsNullPointerException()
            throws IOException, IllegalValueException {
        thrown.expect(NullPointerException.class);
        exportManager.saveFilteredPersons(null, EXPORT_FILE_PATH);
    }

    @Test
    public void saveFilteredPersons_emptyFilteredPersons_throwsIllegalValueException()
            throws IOException, IllegalValueException {
        modelManager.resetData(new AddressBook());

        thrown.expect(IllegalValueException.class);
        exportManager.saveFilteredPersons(filteredPersons, EXPORT_FILE_PATH);
    }

    @Test
    public void getExportFilePath() {
        assertNotNull(exportManager.getExportFilePath());
    }
}
