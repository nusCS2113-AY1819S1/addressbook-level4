package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores RecruitBook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given CandidateBook data to the specified file.
     */
    public static void saveCandidateBookToFile(Path file, XmlSerializableCandidateBook candidateBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, candidateBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }


    /**
     * Returns CandidateBook in the file or an empty address book
     */
    public static XmlSerializableCandidateBook loadCandidateBookFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCandidateBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * Saves the given JobBook data to the specified file.
     */
    public static void saveJobBookToFile(Path file, XmlSerializableJobBook jobBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(file, jobBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns CandidateNook in the file or an empty address book
     */
    public static XmlSerializableJobBook loadJobBookFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableJobBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
