package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores club budget elements book data in an XML file
 */
public class XmlClubBudgetElementsFileStorage {
    /**
     * Saves the given club budget elements book data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableClubBudgetElementsBook clubBudgetElementsBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, clubBudgetElementsBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns club budget elements book in the file or an empty club budget elements book
     */
    public static XmlSerializableClubBudgetElementsBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableClubBudgetElementsBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
