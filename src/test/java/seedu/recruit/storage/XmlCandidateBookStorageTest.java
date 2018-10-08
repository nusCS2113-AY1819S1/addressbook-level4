package seedu.recruit.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.recruit.testutil.TypicalPersons.ALICE;
import static seedu.recruit.testutil.TypicalPersons.HOON;
import static seedu.recruit.testutil.TypicalPersons.IDA;
import static seedu.recruit.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.recruit.commons.exceptions.DataConversionException;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.model.ReadOnlyCandidateBook;

public class XmlCandidateBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlCandidateBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyCandidateBook> readAddressBook(String filePath) throws Exception {
        return new XmlCandidateBookStorage(Paths.get(filePath)).readCandidateBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readAddressBook("NotXmlFormatCandidateBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPersonCandidateBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonCandidateBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        CandidateBook original = getTypicalAddressBook();
        XmlCandidateBookStorage xmlCandidateBookStorage = new XmlCandidateBookStorage(filePath);

        //Save in new file and read back
        xmlCandidateBookStorage.saveCandidateBook(original, filePath);
        ReadOnlyCandidateBook readBack = xmlCandidateBookStorage.readCandidateBook(filePath).get();
        assertEquals(original, new CandidateBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        xmlCandidateBookStorage.saveCandidateBook(original, filePath);
        readBack = xmlCandidateBookStorage.readCandidateBook(filePath).get();
        assertEquals(original, new CandidateBook(readBack));

        //Save and read without specifying file path
        original.addPerson(IDA);
        xmlCandidateBookStorage.saveCandidateBook(original); //file path not specified
        readBack = xmlCandidateBookStorage.readCandidateBook().get(); //file path not specified
        assertEquals(original, new CandidateBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyCandidateBook addressBook, String filePath) {
        try {
            new XmlCandidateBookStorage(Paths.get(filePath))
                    .saveCandidateBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new CandidateBook(), null);
    }


}
