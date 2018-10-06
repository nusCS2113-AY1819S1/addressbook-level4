package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalItems.ARDUINO;
import static seedu.address.testutil.TypicalItems.getTypicalStockList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStockList;
import seedu.address.model.StockList;

public class XmlStockListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlStockListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readStockList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readStockList(null);
    }

    private java.util.Optional<ReadOnlyStockList> readStockList(String filePath) throws Exception {
        return new XmlStockListStorage(Paths.get(filePath)).readStockList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readStockList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readStockList("NotXmlFormatStockList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readStockList_invalidItemStockList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readStockList("invalidItemStockList.xml");
    }

    @Test
    public void readStockList_invalidAndValidItemStockList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readStockList("invalidAndValidItemStockList.xml");
    }

    @Test
    public void readAndSaveStockList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempStockList.xml");
        StockList original = getTypicalStockList();
        XmlStockListStorage xmlStockListStorage = new XmlStockListStorage(filePath);

        //Save in new file and read back
        xmlStockListStorage.saveStockList(original, filePath);
        ReadOnlyStockList readBack = xmlStockListStorage.readStockList(filePath).get();
        assertEquals(original, new StockList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.removeItem(ARDUINO);
        xmlStockListStorage.saveStockList(original, filePath);
        readBack = xmlStockListStorage.readStockList(filePath).get();
        assertEquals(original, new StockList(readBack));

        //Save and read without specifying file path
        original.addItem(ARDUINO);
        xmlStockListStorage.saveStockList(original); //file path not specified
        readBack = xmlStockListStorage.readStockList().get(); //file path not specified
        assertEquals(original, new StockList(readBack));

    }

    @Test
    public void saveStockList_nullStockList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveStockList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code stockList} at the specified {@code filePath}.
     */
    private void saveStockList(ReadOnlyStockList stockList, String filePath) {
        try {
            new XmlStockListStorage(Paths.get(filePath))
                    .saveStockList(stockList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveStockList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveStockList(new StockList(), null);
    }


}
