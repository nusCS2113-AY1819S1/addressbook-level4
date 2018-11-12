package seedu.address.storage.recruitment;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores addressbook data in an XML file
 */
public class XmlRecruitmentFileStorage {


    /**
     * Saves the given recruitmentlist data to the specified file.
     */
    public static void saveDataToFile(Path file, XmlSerializableRecruitmentList recruitmentList)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, recruitmentList);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns recruitment list in the file or an empty recruitment list
     */
    public static XmlSerializableRecruitmentList loadDataFromSaveRecruitmentListFile(Path file)
            throws DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableRecruitmentList.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
