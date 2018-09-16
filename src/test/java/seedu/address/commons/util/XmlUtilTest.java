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

import seedu.address.model.AddressBook;
import seedu.address.storage.XmlAdaptedRecord;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.RecordBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validAddressBook.xml");
    private static final Path MISSING_RECORD_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingRecordField.xml");
    private static final Path INVALID_RECORD_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidRecordField.xml");
    private static final Path VALID_RECORD_FILE = TEST_DATA_FOLDER.resolve("validRecord.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml");

    private static final String INVALID_DATE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_DATE = "23-10-2010";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, AddressBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        AddressBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableAddressBook.class).toModelType();
        assertEquals(9, dataFromFile.getRecordList().size());
    }

    @Test
    public void xmlAdaptedRecordFromFile_fileWithMissingRecordField_validResult() throws Exception {
        XmlAdaptedRecord actualRecord = XmlUtil.getDataFromFile(
                MISSING_RECORD_FIELD_FILE, XmlAdaptedRecordWithRootElement.class);
        XmlAdaptedRecord expectedRecord = new XmlAdaptedRecord(
                null, VALID_DATE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRecord, actualRecord);
    }

    @Test
    public void xmlAdaptedRecordFromFile_fileWithInvalidRecordField_validResult() throws Exception {
        XmlAdaptedRecord actualRecord = XmlUtil.getDataFromFile(
                INVALID_RECORD_FIELD_FILE, XmlAdaptedRecordWithRootElement.class);
        XmlAdaptedRecord expectedRecord = new XmlAdaptedRecord(
                VALID_NAME, INVALID_DATE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRecord, actualRecord);
    }

    @Test
    public void xmlAdaptedRecordFromFile_fileWithValidRecord_validResult() throws Exception {
        XmlAdaptedRecord actualRecord = XmlUtil.getDataFromFile(
                VALID_RECORD_FILE, XmlAdaptedRecordWithRootElement.class);
        XmlAdaptedRecord expectedRecord = new XmlAdaptedRecord(
                VALID_NAME, VALID_DATE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedRecord, actualRecord);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new AddressBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new AddressBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableAddressBook dataToWrite = new XmlSerializableAddressBook(new AddressBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);

        AddressBookBuilder builder = new AddressBookBuilder(new AddressBook());
        dataToWrite = new XmlSerializableAddressBook(
                builder.withRecord(new RecordBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedRecord}
     * objects.
     */
    @XmlRootElement(name = "record")
    private static class XmlAdaptedRecordWithRootElement extends XmlAdaptedRecord {}
}
