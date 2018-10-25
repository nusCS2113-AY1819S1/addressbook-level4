package seedu.address.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalBooks.ALICE;
import static seedu.address.testutil.TypicalBooks.HOON;
import static seedu.address.testutil.TypicalBooks.IDA;
import static seedu.address.testutil.TypicalBooks.getTypicalBookInventory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BookInventory;
import seedu.address.model.ReadOnlyBookInventory;

public class XmlBookInventoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlBookInventoryStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readBookInventory_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readBookInventory(null);
    }

    private java.util.Optional<ReadOnlyBookInventory> readBookInventory(String filePath) throws Exception {
        return new XmlBookInventoryStorage(Paths.get(filePath)).readBookInventory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBookInventory("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readBookInventory("NotXmlFormatBookInventory.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         *
         * */
    }

    @Test
    public void readBookInventory_invalidBookBookInventory_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readBookInventory("invalidBookBookInventory.xml");
    }

    @Test
    public void readBookInventory_invalidAndValidBookBookInventory_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readBookInventory("invalidAndValidBookBookInventory.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        BookInventory original = getTypicalBookInventory();
        XmlBookInventoryStorage xmlAddressBookStorage = new XmlBookInventoryStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyBookInventory readBack = xmlAddressBookStorage.readBookInventory(filePath).get();
        assertEquals(original.toString(), new BookInventory(readBack).toString());

        //Modify data, overwrite exiting file, and read back
        original.addBook(HOON);
        original.removeBook(ALICE);
        xmlAddressBookStorage.saveAddressBook(original, filePath);
        readBack = xmlAddressBookStorage.readBookInventory(filePath).get();
        assertEquals(original.toString(), new BookInventory(readBack).toString());

        //Save and read without specifying file path
        original.addBook(IDA);
        xmlAddressBookStorage.saveAddressBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readBookInventory().get(); //file path not specified
        assertEquals(original.toString(), new BookInventory(readBack).toString());
    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyBookInventory addressBook, String filePath) {
        try {
            new XmlBookInventoryStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new BookInventory(), null);
    }


}
