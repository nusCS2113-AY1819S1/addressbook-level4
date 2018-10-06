package seedu.address.storage;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.XmlUtil;

/**
 * Stores Candidatebook data in an XML file
 */
public class XmlFileStorage {
    /**
     * Saves the given Candidatebook data to the specified file.
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
     * Returns address book in the file or an empty address book
     */
    public static XmlSerializableCandidateBook loadCandidateBookFromSaveFile(Path file) throws DataConversionException,
                                                                            FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(file, XmlSerializableCandidateBook.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

}
