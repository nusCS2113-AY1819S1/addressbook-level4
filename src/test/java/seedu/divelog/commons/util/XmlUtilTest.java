package seedu.divelog.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.divelog.model.DiveLog;
import seedu.divelog.storage.XmlAdaptedDiveSession;
import seedu.divelog.storage.XmlSerializableDiveLog;
import seedu.divelog.testutil.DiveLogBuilder;
import seedu.divelog.testutil.DiveSessionBuilder;
import seedu.divelog.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validDiveLog.xml");
    private static final Path MISSING_DIVE_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingDiveField.xml");
    private static final Path VALID_DIVE_FILE = TEST_DATA_FOLDER.resolve("validDive.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml");

    private static final String VALID_START = "0700";
    private static final String VALID_END = "0800";
    private static final String VALID_SAFETY_STOP = "0745";
    private static final String VALID_PRESSURE_GROUP_START = "A";
    private static final String VALID_PRESSURE_GROUP_END = "F";
    private static final String VALID_DATE = "01012018";
    private static final String VALID_TIMEZONE = "-8";
    private static final String VALID_LOCATION = "Bali";
    private static final float VALID_DEPTH = 5;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, DiveLog.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, DiveLog.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, DiveLog.class);
    }

    //    @Test
    //    public void getDataFromFile_validFile_validResult() throws Exception {
    //        DiveLog dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableDiveLog.class).toModelType();
    //        assertEquals(1, dataFromFile.getDiveList().size());
    //    }


    @Test
    public void xmlAdaptedDiveFromFile_fileWithValidDive_validResult() throws Exception {
        XmlAdaptedDiveSession actualDive = XmlUtil.getDataFromFile(
                VALID_DIVE_FILE, XmlAdaptedDiveSessionWithRootElement.class);
        XmlAdaptedDiveSession expectedDive = new XmlAdaptedDiveSession(
                VALID_DATE, VALID_START, VALID_SAFETY_STOP, VALID_DATE, VALID_END,
                VALID_PRESSURE_GROUP_START, VALID_PRESSURE_GROUP_END,
                VALID_LOCATION, VALID_DEPTH, VALID_TIMEZONE);
        assertEquals(expectedDive, actualDive);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new DiveLog());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new DiveLog());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableDiveLog dataToWrite = new XmlSerializableDiveLog(new DiveLog());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableDiveLog dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDiveLog.class);
        assertEquals(dataToWrite, dataFromFile);

        DiveLogBuilder builder = new DiveLogBuilder(new DiveLog());
        dataToWrite = new XmlSerializableDiveLog(
                builder.withDive(new DiveSessionBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableDiveLog.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of
     * .xml data to {@code XmlAdaptedDiveSession} objects.
     */
    @XmlRootElement(name = "dives")
    private static class XmlAdaptedDiveSessionWithRootElement extends XmlAdaptedDiveSession {
    }
}
