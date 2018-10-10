package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.StockList;
import seedu.address.storage.XmlAdaptedItem;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableStockList;
import seedu.address.testutil.ItemBuilder;
import seedu.address.testutil.StockListBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validStockList.xml");
    private static final Path MISSING_ITEM_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingItemField.xml");
    private static final Path INVALID_ITEM_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidItemField.xml");
    private static final Path VALID_ITEM_FILE = TEST_DATA_FOLDER.resolve("validItem.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempStockList.xml");

    private static final String INVALID_QUANTITY = "-1";

    private static final String VALID_NAME = "Arduino";
    private static final String VALID_QUANTITY = "20";
    private static final String VALID_MIN_QUANTITY = "5";
    private static final List<Integer> VALID_STATUS = new ArrayList<>(Arrays.asList(20, 0, 0));
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("Lab1"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, StockList.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, StockList.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, StockList.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        StockList dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableStockList.class).toModelType();
        assertEquals(3, dataFromFile.getItemList().size());
    }

    /*
    @Test
    public void xmlAdaptedItemFromFile_fileWithMissingItemField_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil.getDataFromFile(
                MISSING_ITEM_FIELD_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedItem = new XmlAdaptedItem(
                null, VALID_QUANTITY, VALID_MIN_QUANTITY, VALID_STATUS, VALID_TAGS);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void xmlAdaptedItemFromFile_fileWithInvalidItemField_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil.getDataFromFile(
                INVALID_ITEM_FIELD_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedItem = new XmlAdaptedItem(
                VALID_NAME, INVALID_QUANTITY, VALID_MIN_QUANTITY, VALID_STATUS, VALID_TAGS);
        assertEquals(expectedItem, actualItem);
    }

    @Test
    public void xmlAdaptedItemFromFile_fileWithValidItem_validResult() throws Exception {
        XmlAdaptedItem actualItem = XmlUtil.getDataFromFile(
                VALID_ITEM_FILE, XmlAdaptedItemWithRootElement.class);
        XmlAdaptedItem expectedItem = new XmlAdaptedItem(
                VALID_NAME, VALID_QUANTITY, VALID_MIN_QUANTITY, VALID_STATUS, VALID_TAGS);
        assertEquals(expectedItem, actualItem);
    }
    */

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new StockList());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new StockList());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableStockList dataToWrite = new XmlSerializableStockList(new StockList());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableStockList dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableStockList.class);
        assertEquals(dataToWrite, dataFromFile);

        StockListBuilder builder = new StockListBuilder(new StockList());
        dataToWrite = new XmlSerializableStockList(
                builder.withItem(new ItemBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableStockList.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedItem}
     * objects.
     */
    @XmlRootElement(name = "item")
    private static class XmlAdaptedItemWithRootElement extends XmlAdaptedItem {}
}
