package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.BookInventory;
import seedu.address.storage.XmlAdaptedBook;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableBookInventory;
import seedu.address.testutil.BookBuilder;
import seedu.address.testutil.BookInventoryBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validBookInventory.xml");
    private static final Path MISSING_BOOK_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingBookField.xml");
    private static final Path INVALID_BOOK_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidBookField.xml");
    private static final Path VALID_BOOK_FILE = TEST_DATA_FOLDER.resolve("validBook.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempBookInventory.xml");

    private static final String INVALID_ISBN = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_ISBN = "978-3-16-148410-0";
    private static final String VALID_COST = "19.99";
    private static final String VALID_PRICE = "29.99";
    private static final String VALID_QUANTITY = "4";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, BookInventory.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, BookInventory.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, BookInventory.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        BookInventory dataFromFile = XmlUtil.getDataFromFile(
                VALID_FILE, XmlSerializableBookInventory.class).toModelType();
        assertEquals(9, dataFromFile.getBookList().size());
    }

    @Test
    public void xmlAdaptedBookFromFile_fileWithMissingBookField_validResult() throws Exception {
        XmlAdaptedBook actualBook = XmlUtil.getDataFromFile(
                MISSING_BOOK_FIELD_FILE, XmlAdaptedBookWithRootElement.class);
        XmlAdaptedBook expectedBook = new XmlAdaptedBook(
                null, VALID_ISBN, VALID_PRICE, VALID_COST, VALID_QUANTITY, VALID_TAGS);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void xmlAdaptedBookFromFile_fileWithInvalidBookField_validResult() throws Exception {
        XmlAdaptedBook actualBook = XmlUtil.getDataFromFile(
                INVALID_BOOK_FIELD_FILE, XmlAdaptedBookWithRootElement.class);
        XmlAdaptedBook expectedBook = new XmlAdaptedBook(
                VALID_NAME, INVALID_ISBN, VALID_PRICE, VALID_COST, VALID_QUANTITY, VALID_TAGS);
        assertEquals(expectedBook, actualBook);
    }

    @Test
    public void xmlAdaptedBookFromFile_fileWithValidBook_validResult() throws Exception {
        XmlAdaptedBook actualBook = XmlUtil.getDataFromFile(
                VALID_BOOK_FILE, XmlAdaptedBookWithRootElement.class);
        XmlAdaptedBook expectedBook = new XmlAdaptedBook(
                VALID_NAME, VALID_ISBN, VALID_PRICE, VALID_COST, VALID_QUANTITY, VALID_TAGS);
        assertEquals(expectedBook, actualBook);
    }


    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new BookInventory());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new BookInventory());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableBookInventory dataToWrite = new XmlSerializableBookInventory(new BookInventory());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableBookInventory dataFromFile = XmlUtil.getDataFromFile(
                TEMP_FILE, XmlSerializableBookInventory.class);
        assertEquals(dataToWrite, dataFromFile);

        BookInventoryBuilder builder = new BookInventoryBuilder(new BookInventory());
        dataToWrite = new XmlSerializableBookInventory(
                builder.withBook(new BookBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableBookInventory.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedBook}
     * objects.
     */
    @XmlRootElement(name = "book")
    private static class XmlAdaptedBookWithRootElement extends XmlAdaptedBook {}
}
