package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalProducts.APPLE;
import static seedu.address.testutil.TypicalProducts.BANANA;
import static seedu.address.testutil.TypicalProducts.GRAPE;
import static seedu.address.testutil.TypicalProducts.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ProductDatabase;
import seedu.address.model.ReadOnlyProductDatabase;

public class XmlProductDatabaseStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlProductDatabaseStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readProductDatabase_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readProductDatabase(null);
    }

    private java.util.Optional<ReadOnlyProductDatabase> readProductDatabase(String filePath) throws Exception {
        return new XmlProductDatabaseStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readProductDatabase("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readProductDatabase("NotXmlFormatProductDatabase.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readProductDatabase_invalidProductProductDatabase_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readProductDatabase("invalidProductProductDatabase.xml");
    }

    @Test
    public void readProductDatabase_invalidAndValidProductProductDatabase_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readProductDatabase("invalidAndValidProductProductDatabase.xml");
    }

    @Test
    public void readAndSaveProductDatabase_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempProductDatabase.xml");
        ProductDatabase original = getTypicalAddressBook();
        XmlProductDatabaseStorage xmlProductDatabaseStorage = new XmlProductDatabaseStorage(filePath);

        //Save in new file and read back
        xmlProductDatabaseStorage.saveAddressBook(original, filePath);
        ReadOnlyProductDatabase readBack = xmlProductDatabaseStorage.readAddressBook(filePath).get();
        assertEquals(original, new ProductDatabase(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addProduct(APPLE);
        original.removeProduct(GRAPE);
        xmlProductDatabaseStorage.saveAddressBook(original, filePath);
        readBack = xmlProductDatabaseStorage.readAddressBook(filePath).get();
        assertEquals(original, new ProductDatabase(readBack));

        //Save and read without specifying file path
        original.addProduct(BANANA);
        xmlProductDatabaseStorage.saveAddressBook(original); //file path not specified
        readBack = xmlProductDatabaseStorage.readAddressBook().get(); //file path not specified
        assertEquals(original, new ProductDatabase(readBack));

    }

    @Test
    public void saveProductDatabase_nullProductDatabase_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveProductDatabase(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveProductDatabase(ReadOnlyProductDatabase addressBook, String filePath) {
        try {
            new XmlProductDatabaseStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveProductDatabase_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveProductDatabase(new ProductDatabase(), null);
    }
}
