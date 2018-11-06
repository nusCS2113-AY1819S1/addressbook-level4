package seedu.recruit.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.recruit.commons.exceptions.DataConversionException;
import seedu.recruit.commons.util.XmlUtil;

/**
 * Stores RecruitBook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given CandidateBook data to the specified file.
     */
    public static void saveCandidateBookToFile(Path path, XmlSerializableCandidateBook candidateBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(path, candidateBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }


    /**
     * Returns CandidateBook in the file or an empty CandidateBookgit
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
     * Saves the given CompanyBook data to the specified file.
     */
    public static void saveCompanyBookToFile(Path path, XmlSerializableCompanyBook companyBook)
            throws FileNotFoundException {
        try {
            XmlUtil.saveDataToFile(path, companyBook);
        } catch (JAXBException e) {
            throw new AssertionError("Unexpected exception " + e.getMessage(), e);
        }
    }

    /**
     * Returns CompanyBook in the file or an empty CompanyBook
     */
    public static XmlSerializableCompanyBook loadCompanyBookFromSaveFile(Path file) throws DataConversionException,
            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCompanyBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
