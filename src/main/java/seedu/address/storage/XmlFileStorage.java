package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given addressbook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableAddressBook addressBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, addressBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Saves the given expenseBook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableExpenseBook expenseBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, expenseBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableAddressBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableAddressBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    //============= Expense ====================================================================

    /**
     * Returns expense book in the file or an empty expense book
     */
    public static XmlSerializableExpenseBook loadExpenseFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableExpenseBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }


}
