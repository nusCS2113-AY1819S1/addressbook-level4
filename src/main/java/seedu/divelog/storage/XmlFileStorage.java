package seedu.divelog.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.divelog.commons.exceptions.DataConversionException;
import seedu.divelog.commons.util.XmlUtil;

/**
 * Stores divelog data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given divelog data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableDiveLog diveLog)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, diveLog);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns divelog book in the file or an empty divelog book
     */
    public static XmlSerializableDiveLog loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableDiveLog.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
