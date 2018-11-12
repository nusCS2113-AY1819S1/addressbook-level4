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
     * Saves the given tracked data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableTrackedData trackedData)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, trackedData);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Saves the given parameter to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableTrackedDataList trackedDataList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, trackedDataList);
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

    /**
     * Returns tracked data in the file or an empty tracked data list
     */
    public static XmlSerializableTrackedData loadTrackedDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTrackedData.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns tracked data in the file or an empty tracked data list
     */
    public static XmlSerializableTrackedDataList loadTrackedDataListFromSaveFile(Path file)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTrackedDataList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
