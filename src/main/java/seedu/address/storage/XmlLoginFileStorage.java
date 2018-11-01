package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores loginbook data in an XML file
 */
public class XmlLoginFileStorage {
    /**
     * Saves the given loginbook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableLoginBook loginBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, loginBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns login book in the file or an empty login book
     */
    public static XmlSerializableLoginBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                                  FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableLoginBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
