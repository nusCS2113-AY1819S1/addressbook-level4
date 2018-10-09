package seedu.recruit.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.recruit.commons.exceptions.IllegalValueException;
import seedu.recruit.commons.util.XmlUtil;
import seedu.recruit.model.CandidateBook;
import seedu.recruit.testutil.TypicalPersons;

public class XmlSerializableCandidateBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableCandidateBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsCandidateBook.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonCandidateBook.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonCandidateBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableCandidateBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableCandidateBook.class);
        CandidateBook candidateBookFromFile = dataFromFile.toModelType();
        CandidateBook typicalPersonsCandidateBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(candidateBookFromFile, typicalPersonsCandidateBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableCandidateBook dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableCandidateBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableCandidateBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableCandidateBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableCandidateBook.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
