package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores workout book data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given workout book data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableWorkoutBook workoutBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, workoutBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns workout book in the file or an empty workout book
     */
    public static XmlSerializableWorkoutBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableWorkoutBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
