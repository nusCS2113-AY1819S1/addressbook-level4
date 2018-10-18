package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook and todolist data in an XML file
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

    /**
     * Saves the given expendituretracker data to the specified file.
     */
    public static void saveExpenditureTrackerDataToFile(Path file, XmlSerializableExpenditureTracker expenditureTracker)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, expenditureTracker);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns expenditure tracker in the file or an empty expenditure tracker
     */
    public static XmlSerializableExpenditureTracker loadExpenditureTrackerDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableExpenditureTracker.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given todolist data to the specified file.
     */
    public static void saveTodoListDataToFile(Path file, XmlSerializableTodoList todoList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, todoList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns to-do list in the file or an empty to-do list
     */
    public static XmlSerializableTodoList loadTodoListDataFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableTodoList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
