package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.storage.transactions.XmlSerializableTransactionList;

/**
 * Stores inventory list data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given inventorylist data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableInventoryList inventoryList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, inventoryList);
            System.out.println(file.toString());
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns inventory list in the file or an empty address book
     */
    public static XmlSerializableInventoryList loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableInventoryList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }


    /**
     * Saves the given transactionlist data to the specified file.
     */
    public static void saveTransactionDataToFile(Path file, XmlSerializableTransactionList transactionList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, transactionList);
            System.out.println(file.toString());
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns inventory list in the file or an empty address book
     */
    public static XmlSerializableTransactionList loadTransactionDataFromSaveFile(Path file)
            throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTransactionList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }
}
