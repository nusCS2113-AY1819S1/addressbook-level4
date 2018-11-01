package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores finalBudgetsBook data in an XML file
 */
public class XmlFinalBudgetsFileStorage {
    /**
     * Saves the given finalBudgetsBook data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableFinalBudgetsBook finalBudgetsBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, finalBudgetsBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns finalBudgetsBook in the file or an empty finalBudgetsBook
     */
    public static XmlSerializableFinalBudgetsBook loadDataFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableFinalBudgetsBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
