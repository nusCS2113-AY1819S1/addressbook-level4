package seedu.address.storage;

import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.LoginBook;
import seedu.address.model.ReadOnlyLoginBook;

public class XmlLoginBookStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlLoginBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readLoginBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readLoginBook(null);
    }

    private java.util.Optional<ReadOnlyLoginBook> readLoginBook(String filePath) throws Exception {
        return new XmlLoginBookStorage(Paths.get(filePath)).readLoginBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLoginBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readLoginBook("NotXmlFormatLoginBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readLoginBook_invalidAccountLoginBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readLoginBook("invalidAccountLoginBook.xml");
    }

    @Test
    public void readLoginBook_invalidAndValidAccountLoginBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readLoginBook("invalidAndValidAccountLoginBook.xml");
    }

    @Test
    public void saveLoginBook_nullLoginBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLoginBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code loginBook} at the specified {@code filePath}.
     */
    private void saveLoginBook(ReadOnlyLoginBook loginBook, String filePath) {
        try {
            new XmlLoginBookStorage(Paths.get(filePath))
                    .saveLoginBook(loginBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLoginBook(new LoginBook(), null);
    }
}
