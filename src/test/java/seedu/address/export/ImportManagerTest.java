package seedu.address.export;

import static org.junit.Assert.assertEquals;
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
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
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

    private ModelManager modelManager;
    private ImportManager importManager;

    @Before
    public void setUp() {
        modelManager = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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
//
//        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
//        modelManager.resetData(new AddressBook());
//        modelManager.importPersonsFromAddressBook(IMPORT_TYPICAL_ADDRESSBOOK_FILE_PATH);
//
//        assertEquals(modelManager, expectedModel);
    }

//    //@Test
//    public void generateXMLFiles() throws IOException, DataConversionException, IllegalValueException {
//        ReadOnlyAddressBook addressBook = new AddressBookBuilder().withPerson(AMY).withPerson(BOB).build();
//        ModelManager modelManager = new ModelManager(addressBook, new UserPrefs());
//        // modelManager.resetData(new AddressBook());
//        Path FILE_PATH = Paths.get("src", "test", "data",
//                "sandbox", "importTwoPersons.xml");
//        ExportManager exportManager = new ExportManager(modelManager.getAddressBook().getPersonList(), FILE_PATH);
//        // ExportManager exportManager = new ExportManager(addressBook.getPersonList(), TEST_DATA_FOLDER);
//        exportManager.saveFilteredPersons();
//
//        assertEquals(true, true);
////        ImportManager importManager = new ImportManager(TEST_DATA_FOLDER);
////        importManager.readAddressBook();
//    }

//    @Test
//    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
//        thrown.expect(NullPointerException.class);
//        modelManager.readAddressBook(null);
//    }
//
//    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
//        return new XmlAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
//    }
//
//    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
//        return prefsFileInTestDataFolder != null
//                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
//                : null;
//    }
//
//    @Test
//    public void read_missingFile_emptyResult() throws Exception {
//        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
//    }
//
//    @Test
//    public void read_notXmlFormat_exceptionThrown() throws Exception {
//
//        thrown.expect(DataConversionException.class);
//        readAddressBook("NotXmlFormatAddressBook.xml");
//
//        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
//         * That means you should not have more than one exception test in one method
//         */
//    }
//
//    @Test
//    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
//        thrown.expect(DataConversionException.class);
//        readAddressBook("invalidPersonAddressBook.xml");
//    }
//
//    @Test
//    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
//        thrown.expect(DataConversionException.class);
//        readAddressBook("invalidAndValidPersonAddressBook.xml");
//    }
//
//    @Test
//    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
//        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
//        AddressBook original = getTypicalAddressBook();
//        XmlAddressBookStorage xmlAddressBookStorage = new XmlAddressBookStorage(filePath);
//
//        //Save in new file and read back
//        xmlAddressBookStorage.saveAddressBook(original, filePath);
//        ReadOnlyAddressBook readBack = xmlAddressBookStorage.readAddressBook(filePath).get();
//        assertEquals(original, new AddressBook(readBack));
//
//        //Modify data, overwrite exiting file, and read back
//        original.addPerson(HOON);
//        original.removePerson(ALICE);
//        xmlAddressBookStorage.saveAddressBook(original, filePath);
//        readBack = xmlAddressBookStorage.readAddressBook(filePath).get();
//        assertEquals(original, new AddressBook(readBack));
//
//        //Save and read without specifying file path
//        original.addPerson(IDA);
//        xmlAddressBookStorage.saveAddressBook(original); //file path not specified
//        readBack = xmlAddressBookStorage.readAddressBook().get(); //file path not specified
//        assertEquals(original, new AddressBook(readBack));
//
//    }
//
//    @Test
//    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
//        thrown.expect(NullPointerException.class);
//        saveAddressBook(null, "SomeFile.xml");
//    }
//
//    @Test
//    public void backupAddressBook_nullAddressBook_throwsNullPointerException() {
//        thrown.expect(NullPointerException.class);
//        backupAddressBook(null, "SomeBackupFile.xml");
//    }
//
//    /**
//     * Saves {@code addressBook} at the specified {@code filePath}.
//     */
//    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
//        try {
//            new XmlAddressBookStorage(Paths.get(filePath))
//                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
//        } catch (IOException ioe) {
//            throw new AssertionError("There should not be an error writing to the file.", ioe);
//        }
//    }
//
//    @Test
//    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
//        thrown.expect(NullPointerException.class);
//        saveAddressBook(new AddressBook(), null);
//    }
//
//    /**
//     * Backups {@code addressBook} at the specified {@code filePath}.
//     */
//    private void backupAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
//        try {
//            new XmlAddressBookStorage(Paths.get(filePath))
//                    .backupAddressBook(addressBook);
//        } catch (IOException ioe) {
//            throw new AssertionError("There should not be an error writing to the file.", ioe);
//        }
//    }

    @Test
    public void getImportPath() {
        assertNotNull(importManager.getImportPath());
    }

}
