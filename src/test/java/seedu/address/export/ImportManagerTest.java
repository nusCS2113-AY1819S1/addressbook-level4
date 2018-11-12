package seedu.address.export;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.testutil.AddressBookBuilder;

//@@author jitwei98
public class ImportManagerTest {
    private static final Path IMPORT_FILE_PATH = Paths.get("src", "test", "data",
            "sandbox", "sampleData.xml");
    private static final Path IMPORT_TYPICAL_ADDRESSBOOK_FILE_PATH = Paths.get("src", "test", "data",
            "sandbox", "importTypicalAddressbook.xml");
    private static final Path IMPORT_TWO_PERSONS_FILE_PATH = Paths.get("src", "test", "data",
            "sandbox", "importTwoPersons.xml");
    private static final Path IMPORT_EMPTY_ADDRESSBOOK_FILE_PATH = Paths.get("src", "test", "data",
            "sandbox", "importEmptyAddressbook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ImportManager importManager;

    @Before
    public void setUp() {
        importManager = new ImportManager(IMPORT_FILE_PATH);
    }

    @Before
    public void exportTypicalPersonsXmlFile() throws IOException, IllegalValueException {
        ReadOnlyAddressBook addressBook = getTypicalAddressBook();

        ExportManager exportManager = new ExportManager(addressBook.getPersonList(),
                IMPORT_TYPICAL_ADDRESSBOOK_FILE_PATH);
        exportManager.saveFilteredPersons();
    }

    @Before
    public void exportTwoPersonsXmlFile() throws IOException, IllegalValueException {
        ReadOnlyAddressBook addressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        ExportManager exportManager = new ExportManager(addressBook.getPersonList(), IMPORT_TWO_PERSONS_FILE_PATH);
        exportManager.saveFilteredPersons();
    }

    @Before
    public void exportEmptyXmlFile() throws IOException {
        ReadOnlyAddressBook addressBook = new AddressBook();

        ObservableList<Person> filteredPersons = addressBook.getPersonList();

        // Cannot export using ExportManager since the filteredPersons is empty
        FileUtil.createIfMissing(IMPORT_EMPTY_ADDRESSBOOK_FILE_PATH);
        XmlFileStorage.saveDataToFile(IMPORT_EMPTY_ADDRESSBOOK_FILE_PATH,
                new XmlSerializableAddressBook(filteredPersons));
    }

    @Test
    public void readAddressBook_withTwoPersons_success() throws IOException, DataConversionException {
        importManager = new ImportManager(IMPORT_TWO_PERSONS_FILE_PATH);
        ReadOnlyAddressBook addressBook = importManager.readAddressBook().orElseThrow(IOException::new);
        ReadOnlyAddressBook expectedAddressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();

        assertEquals(addressBook, expectedAddressBook);
    }

    @Test
    public void readAddressBook_withNoPerson_success() throws IOException, DataConversionException {
        importManager = new ImportManager(IMPORT_EMPTY_ADDRESSBOOK_FILE_PATH);
        ReadOnlyAddressBook addressBook = importManager.readAddressBook().orElseThrow(IOException::new);
        ReadOnlyAddressBook expectedAddressBook = new AddressBookBuilder().build();

        assertEquals(addressBook, expectedAddressBook);
    }

    @Test
    public void readAddressBook_withTypicalPersons_success()
            throws IOException, DataConversionException {
        importManager = new ImportManager(IMPORT_TYPICAL_ADDRESSBOOK_FILE_PATH);
        ReadOnlyAddressBook addressBook = importManager.readAddressBook().orElseThrow(IOException::new);
        ReadOnlyAddressBook expectedAddressBook = getTypicalAddressBook();

        assertEquals(addressBook, expectedAddressBook);
    }


    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        importManager = new ImportManager(null);
        thrown.expect(NullPointerException.class);
        importManager.readAddressBook();
    }


    @Test
    public void readAddressBook_missingFile_emptyResult() throws Exception {
        importManager = new ImportManager(Paths.get("NonExistentFile.xml"));
        assertFalse(importManager.readAddressBook().isPresent());
    }

    @Test
    public void readAddressBook_notXmlFormat_exceptionThrown() throws Exception {
        importManager = new ImportManager(Paths.get("src", "test", "data",
                "sandbox", "addressbook.csv"));
        thrown.expect(DataConversionException.class);
        importManager.readAddressBook();
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        // Borrow the test data from XmlAddressBookStorageTest
        importManager = new ImportManager(Paths.get("src", "test", "data",
                "XmlAddressBookStorageTest", "invalidPersonAddressBook.xml"));
        thrown.expect(DataConversionException.class);
        importManager.readAddressBook();
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        // Borrow the test data from XmlAddressBookStorageTest
        importManager = new ImportManager(Paths.get("src", "test", "data",
                "XmlAddressBookStorageTest", "invalidAndValidPersonAddressBook.xml"));
        thrown.expect(DataConversionException.class);
        importManager.readAddressBook();
    }

    @Test
    public void getImportPath() {
        assertNotNull(importManager.getImportPath());
    }

}
