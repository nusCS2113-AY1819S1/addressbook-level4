package seedu.address.storage;

import static junit.framework.TestCase.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.BookInventory;
import seedu.address.testutil.TypicalBooks;


public class XmlSerializableBookInventoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableBookInventoryTest");
    private static final Path TYPICAL_BOOKS_FILE = TEST_DATA_FOLDER.resolve("typicalBooksBookInventory.xml");
    private static final Path INVALID_BOOK_FILE = TEST_DATA_FOLDER.resolve("invalidBookBookInventory.xml");
    private static final Path DUPLICATE_BOOK_FILE = TEST_DATA_FOLDER.resolve("duplicateBookBookInventory.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalBooksFile_success() throws Exception {
        XmlSerializableBookInventory dataFromFile = XmlUtil.getDataFromFile(TYPICAL_BOOKS_FILE,
                XmlSerializableBookInventory.class);
        BookInventory bookInventoryFromFile = dataFromFile.toModelType();
        BookInventory typicalBooksBookInventory = TypicalBooks.getTypicalBookInventory();
        assertEquals(bookInventoryFromFile.toString(), typicalBooksBookInventory.toString());
    }

    @Test
    public void toModelType_invalidBookFile_throwsIllegalValueException() throws Exception {
        XmlSerializableBookInventory dataFromFile = XmlUtil.getDataFromFile(INVALID_BOOK_FILE,
                XmlSerializableBookInventory.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateBooks_throwsIllegalValueException() throws Exception {
        XmlSerializableBookInventory dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_BOOK_FILE,
                XmlSerializableBookInventory.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableBookInventory.MESSAGE_DUPLICATE_BOOK);
        dataFromFile.toModelType();
    }
}