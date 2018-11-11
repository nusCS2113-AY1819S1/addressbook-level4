package seedu.address.storage;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static seedu.address.testutil.TypicalDistributors.AHHUAT;
//import static seedu.address.testutil.TypicalDistributors.AHLONG;
//import static seedu.address.testutil.TypicalDistributors.AHTING;
//import static seedu.address.testutil.TypicalDistributors.getTypicalDistributorBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DistributorBook;
import seedu.address.model.ReadOnlyDistributorBook;

public class XmlDistributorBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths
            .get("src", "test", "data", "XmlDistributorBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readDistributorBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readDistributorBook(null);
    }

    private java.util.Optional<ReadOnlyDistributorBook> readDistributorBook(String filePath) throws Exception {
        return new XmlDistributorBookStorage(Paths.get(filePath))
                .readDistributorBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDistributorBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readDistributorBook("NotXmlFormatProductDatabase.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readDistributorBook_invalidPersonDistributorBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDistributorBook("invalidProductProductDatabase.xml");
    }

    @Test
    public void readDistributorBook_invalidAndValidDistributorDistributorBook_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readDistributorBook("invalidAndValidProductProductDatabase.xml");
    }

    /*@Test
    public void readAndSaveDistributorBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempDistributorBook.xml");
        DistributorBook original = getTypicalDistributorBook();
        XmlDistributorBookStorage xmlDistributorBookStorage = new XmlDistributorBookStorage(filePath);

        //Save in new file and read back
        xmlDistributorBookStorage.saveDistributorBook(original, filePath);
        ReadOnlyDistributorBook readBack = xmlDistributorBookStorage.readDistributorBook(filePath).get();
        assertEquals(original, new DistributorBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addDistributor(AHLONG);
        original.removeDistributor(AHHUAT);
        xmlDistributorBookStorage.saveDistributorBook(original, filePath);
        readBack = xmlDistributorBookStorage.readDistributorBook(filePath).get();
        assertEquals(original, new DistributorBook(readBack));

        //Save and read without specifying file path
        original.addDistributor(AHTING);
        xmlDistributorBookStorage.saveDistributorBook(original); //file path not specified
        readBack = xmlDistributorBookStorage.readDistributorBook().get(); //file path not specified
        assertEquals(original, new DistributorBook(readBack));

    }
    */
    @Test
    public void saveDistributorBook_nullDistributorBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDistributorBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code distributorBook} at the specified {@code filePath}.
     */
    private void saveDistributorBook(ReadOnlyDistributorBook distributorBook, String filePath) {
        try {
            new XmlDistributorBookStorage(Paths.get(filePath))
                    .saveDistributorBook(distributorBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDistributorBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDistributorBook(new DistributorBook(), null);
    }


}
