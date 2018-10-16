package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.util.XmlUtil;
import seedu.address.model.BookInventory;
import seedu.address.testutil.TypicalBooks;

public class XmlSerializableBookInventoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableBookInventoryTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.xml");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.xml");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        XmlSerializableBookInventory dataFromFile = XmlUtil.getDataFromFile(TYPICAL_PERSONS_FILE,
                XmlSerializableBookInventory.class);
        BookInventory bookInventoryFromFile = dataFromFile.toModelType();
        BookInventory typicalPersonsBookInventory = TypicalBooks.getTypicalBookInventory();
        // assertEquals(bookInventoryFromFile, typicalPersonsBookInventory);
    }
    /*
    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        XmlSerializableBookInventory dataFromFile = XmlUtil.getDataFromFile(INVALID_PERSON_FILE,
                XmlSerializableBookInventory.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        XmlSerializableBookInventory dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_PERSON_FILE,
                XmlSerializableBookInventory.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableBookInventory.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }
    */
}
