package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.AddressBook;

//@@author chelseyong
public class XmlSerializableTaskBookTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableTaskBookTest");
    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTaskBook.xml");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskBook.xml");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTasksInTaskBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_TASKS_FILE,
                XmlSerializableTaskBook.class);
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = getTypicalTaskBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(INVALID_TASK_FILE,
                XmlSerializableTaskBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableTaskBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_TASK_FILE,
                XmlSerializableTaskBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableTaskBook.MESSAGE_DUPLICATE_TASK);
        dataFromFile.toModelType();
    }

}
