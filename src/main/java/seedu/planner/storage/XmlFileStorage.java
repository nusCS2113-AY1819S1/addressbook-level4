package seedu.planner.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.util.XmlUtil;

/**
 * Stores financialplanner data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given financialplanner data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableFinancialPlanner financialPlanner)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, financialPlanner);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns financial planner in the file or an empty financial planner
     */
    public static XmlSerializableFinancialPlanner loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableFinancialPlanner.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
