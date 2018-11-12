package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.request.requeststorage.XmlSerializableRequestList;

/**
 * Stores bookinventory data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given bookinventory data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableBookInventory addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * saves request to the filr
     * @param file storage
     * @param requestList list of request in storage
     * @throws FileNotFoundException unable to find found
     */
    public static void saveRequestToFile(Path file, XmlSerializableRequestList requestList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, requestList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }
    /**
     * Returns BookInventory in the file or an empty BookInventory
     */
    public static XmlSerializableBookInventory loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableBookInventory.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Returns BookInventory in the file or an empty BookInventory
     */
    public static XmlSerializableRequestList loadRequestsFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableRequestList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
