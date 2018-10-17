package seedu.planner.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.util.XmlUtil;
import seedu.planner.storage.xmljaxb.XmlSerializableClass;

/**
 * Stores any {@code XmlSerializableClass} data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given object to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableClass financialPlanner)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, financialPlanner);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns the {@code XmlSerializableClass} object serialized in the file or an empty object
     */
    public static <T> T loadDataFromSaveFile(Path file, Class<T> classToConvert) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, classToConvert);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
