package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook and user database data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableProductDatabase addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableProductDatabase loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableProductDatabase.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
    /**
     * Saves the given user database data to the specified file.
     */
    public static void saveUsersToFile(Path file, XmlSerializableUserDatabase userDatabase)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, userDatabase);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Returns user database in the file or an empty address book
     */
    public static XmlSerializableUserDatabase loadUsersFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableUserDatabase.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
