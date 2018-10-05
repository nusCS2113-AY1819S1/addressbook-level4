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
import seedu.address.model.LoginBook;
import seedu.address.storage.XmlAccount;
import seedu.address.storage.XmlAdaptedPerson;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableAddressBook;
import seedu.address.storage.XmlSerializableLoginBook;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_LOGIN_FILE = TEST_DATA_FOLDER.resolve("emptyLogin.xml");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_LOGIN_FILE = TEST_DATA_FOLDER.resolve("missingLogin.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_LOGIN_FILE = TEST_DATA_FOLDER.resolve("validLoginBook.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validAddressBook.xml");
    private static final Path MISSING_LOGINDETAILS_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingLoginDetailField.xml");
    private static final Path MISSING_PERSON_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingPersonField.xml");
    private static final Path INVALID_LOGINDETAILS_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidLoginDetailField.xml");
    private static final Path INVALID_PERSON_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidPersonField.xml");
    private static final Path VALID_LOGINDETAILS_FILE = TEST_DATA_FOLDER.resolve("validLoginDetails.xml");
    private static final Path VALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("validPerson.xml");
    private static final Path TEMP_LOGIN_FILE = TestUtil.getFilePathInSandboxFolder("tempLoginBook.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempAddressBook.xml");

    private static final String INVALID_USERID = "AA234567M";
    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_USERID = "A1234567M";
    private static final String VALID_USERPASSWORD = "zaq1xsw2cde3";
    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, LoginBook.class);
        XmlUtil.getDataFromFile(null, AddressBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_LOGIN_FILE, null);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_LOGIN_FILE, AddressBook.class);
        XmlUtil.getDataFromFile(MISSING_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_LOGIN_FILE, AddressBook.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, AddressBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        LoginBook dataFromLoginFile = XmlUtil.getDataFromFile(VALID_LOGIN_FILE,
                XmlSerializableLoginBook.class).toModelType();
        assertEquals(4, dataFromLoginFile.getLoginDetailsList().size());
        AddressBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableAddressBook.class).toModelType();
        assertEquals(9, dataFromFile.getPersonList().size());
    }

    @Test
    public void xmlAccountFromFile_fileWithMissingLoginDetailsField_validResult() throws Exception {
        XmlAccount actualLoginDetail = XmlUtil.getDataFromFile(
                MISSING_LOGINDETAILS_FIELD_FILE, XmlAccountWithRootElement.class);
        XmlAccount expectedLoginDetail = new XmlAccount(null, VALID_USERPASSWORD);
        assertEquals(expectedLoginDetail, actualLoginDetail);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithMissingPersonField_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                MISSING_PERSON_FIELD_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAccountFromFile_fileWithInvalidLoginDetailsField_validResult() throws Exception {
        XmlAccount actualLoginDetail = XmlUtil.getDataFromFile(
                INVALID_LOGINDETAILS_FIELD_FILE, XmlAccountWithRootElement.class);
        XmlAccount expectedLoginDetail = new XmlAccount(INVALID_USERID, VALID_USERPASSWORD);
        assertEquals(expectedLoginDetail, actualLoginDetail);
    }

    @Test
    public void xmlAdaptedPersonFromFile_fileWithInvalidPersonField_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                INVALID_PERSON_FIELD_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void xmlAccountFromFile_fileWithValidLoginDetails_validResult() throws Exception {
        XmlAccount actualLoginDetail = XmlUtil.getDataFromFile(
                VALID_LOGINDETAILS_FILE, XmlAccountWithRootElement.class);
        XmlAccount expectedLoginDetail = new XmlAccount(VALID_USERID, VALID_USERPASSWORD);
        assertEquals(expectedLoginDetail, actualLoginDetail);
    }


    @Test
    public void xmlAdaptedPersonFromFile_fileWithValidPerson_validResult() throws Exception {
        XmlAdaptedPerson actualPerson = XmlUtil.getDataFromFile(
                VALID_PERSON_FILE, XmlAdaptedPersonWithRootElement.class);
        XmlAdaptedPerson expectedPerson = new XmlAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_TAGS);
        assertEquals(expectedPerson, actualPerson);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new LoginBook());
        XmlUtil.saveDataToFile(null, new AddressBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_LOGIN_FILE, null);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_LOGIN_FILE, new AddressBook());
        XmlUtil.saveDataToFile(MISSING_FILE, new AddressBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_LOGIN_FILE);
        XmlSerializableLoginBook loginDataToWrite = new XmlSerializableLoginBook(new LoginBook());
        XmlUtil.saveDataToFile(TEMP_LOGIN_FILE, loginDataToWrite);
        XmlSerializableLoginBook loginDataFromFile = XmlUtil.getDataFromFile(TEMP_LOGIN_FILE,
                XmlSerializableLoginBook.class);
        assertEquals(loginDataToWrite, loginDataFromFile);
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableAddressBook dataToWrite = new XmlSerializableAddressBook(new AddressBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableAddressBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);

        AddressBookBuilder builder = new AddressBookBuilder(new AddressBook());
        dataToWrite = new XmlSerializableAddressBook(
                builder.withPerson(new PersonBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableAddressBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedPerson}
     * objects.
     */
    @XmlRootElement(name = "person")
    private static class XmlAdaptedPersonWithRootElement extends XmlAdaptedPerson {}

    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAccount}
     * objects.
     */
    @XmlRootElement(name = "logindetail")
    private static class XmlAccountWithRootElement extends XmlAccount {}
}
