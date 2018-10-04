package com.t13g2.forum.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.t13g2.forum.commons.exceptions.DataConversionException;
import com.t13g2.forum.model.ForumBook;
import com.t13g2.forum.model.ReadOnlyForumBook;
import com.t13g2.forum.testutil.TypicalPersons;

public class XmlForumBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlForumBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readAddressBook(null);
    }

    private java.util.Optional<ReadOnlyForumBook> readAddressBook(String filePath) throws Exception {
        return new XmlForumBookStorage(Paths.get(filePath)).readForumBook(addToTestDataPathIfNotNull(filePath));
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
        readAddressBook("NotXmlFormatAddressBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidPersonAddressBook.xml");
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readAddressBook("invalidAndValidPersonAddressBook.xml");
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        ForumBook original = TypicalPersons.getTypicalAddressBook();
        XmlForumBookStorage xmlAddressBookStorage = new XmlForumBookStorage(filePath);

        //Save in new file and read back
        xmlAddressBookStorage.saveForumBook(original, filePath);
        ReadOnlyForumBook readBack = xmlAddressBookStorage.readForumBook(filePath).get();
        assertEquals(original, new ForumBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addPerson(TypicalPersons.HOON);
        original.removePerson(TypicalPersons.ALICE);
        xmlAddressBookStorage.saveForumBook(original, filePath);
        readBack = xmlAddressBookStorage.readForumBook(filePath).get();
        assertEquals(original, new ForumBook(readBack));

        //Save and read without specifying file path
        original.addPerson(TypicalPersons.IDA);
        xmlAddressBookStorage.saveForumBook(original); //file path not specified
        readBack = xmlAddressBookStorage.readForumBook().get(); //file path not specified
        assertEquals(original, new ForumBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyForumBook addressBook, String filePath) {
        try {
            new XmlForumBookStorage(Paths.get(filePath))
                    .saveForumBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveAddressBook(new ForumBook(), null);
    }


}
