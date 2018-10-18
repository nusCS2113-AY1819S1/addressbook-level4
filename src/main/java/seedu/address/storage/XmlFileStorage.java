package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.nio.file.Path;

/**
 * Stores event manager data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given event manager data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableEManager addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty event manager
     */
    public static XmlSerializableEManager loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableEManager.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
