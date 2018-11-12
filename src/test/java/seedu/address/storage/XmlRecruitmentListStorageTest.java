package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE3;
import static seedu.address.testutil.TypicalRecruitments.RECRUITMENT_EXAMPLE4;
import static seedu.address.testutil.TypicalRecruitments.getTypicalRecruitmentList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.recruitment.ReadOnlyRecruitmentList;
import seedu.address.model.recruitment.RecruitmentList;
import seedu.address.storage.recruitment.XmlRecruitmentListStorage;

public class XmlRecruitmentListStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get(
            "src", "test", "data", "XmlRecruitmentListStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readRecruitmentList_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRecruitmentList(null);
    }

    private java.util.Optional<ReadOnlyRecruitmentList> readRecruitmentList(String filePath) throws Exception {
        return new XmlRecruitmentListStorage(
                Paths.get(filePath)).readRecruitmentList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRecruitmentList("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRecruitmentList("NotXmlFormatRecruitmentList.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readRecruitmentList_invalidRecruitmentRecruitmentList_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRecruitmentList("invalidRecruitmentRecruitmentList.xml");
    }

    @Test
    public void readRecruitmentList_invalidAndValidRecruitmentRecruitmentList_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readRecruitmentList("invalidAndValidRecruitmentRecruitmentList.xml");
    }

    @Test
    public void readAndSaveRecruitmentList_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.xml");
        RecruitmentList original = getTypicalRecruitmentList();
        XmlRecruitmentListStorage xmlRecruitmentListStorage = new XmlRecruitmentListStorage(filePath);

        //Save in new file and read back
        xmlRecruitmentListStorage.saveRecruitmentList(original, filePath);
        ReadOnlyRecruitmentList readBack = xmlRecruitmentListStorage.readRecruitmentList(filePath).get();
        assertEquals(original, new RecruitmentList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addRecruitment(RECRUITMENT_EXAMPLE3);
        original.removeRecruitment(RECRUITMENT_EXAMPLE);
        xmlRecruitmentListStorage.saveRecruitmentList(original, filePath);
        readBack = xmlRecruitmentListStorage.readRecruitmentList(filePath).get();
        assertEquals(original, new RecruitmentList(readBack));

        //Save and read without specifying file path
        original.addRecruitment(RECRUITMENT_EXAMPLE4);
        xmlRecruitmentListStorage.saveRecruitmentList(original); //file path not specified
        readBack = xmlRecruitmentListStorage.readRecruitmentList().get(); //file path not specified
        assertEquals(original, new RecruitmentList(readBack));
    }


    @Test
    public void saveRecruitmentList_nullRecruitmentList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRecruitmentList(null, "SomeFile.xml");
    }

    /**
     * Saves {@code recruitmentList} at the specified {@code filePath}.
     */
    private void saveRecruitmentList(ReadOnlyRecruitmentList recruitmentList, String filePath) {
        try {
            new XmlRecruitmentListStorage(Paths.get(filePath))
                    .saveRecruitmentList(recruitmentList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRecruitmentList_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRecruitmentList(new RecruitmentList(), null);
    }

}
