package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.crypto.SecretKey;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import seedu.address.commons.exceptions.EncryptionException;

/**
 * Helps with reading from and writing to XML files.
 */
public class XmlUtil {
    static {
        org.apache.xml.security.Init.init();
    }
    /**
     * Returns the xml data in the file as an object of the specified type.
     *
     * @param file           Points to a valid xml file containing data that match the {@code classToConvert}.
     *                       Cannot be null.
     * @param classToConvert The class corresponding to the xml data.
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if the file is empty or does not have the correct format.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDataFromFile(Path file, Class<T> classToConvert)
            throws FileNotFoundException, JAXBException {

        requireNonNull(file);
        requireNonNull(classToConvert);

        if (!FileUtil.isFileExists(file)) {
            throw new FileNotFoundException("File not found : " + file.toAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(classToConvert);
        Unmarshaller um = context.createUnmarshaller();

        return ((T) um.unmarshal(file.toFile()));
    }

    /**
     * Saves the data in the file in xml format.
     *
     * @param file Points to a valid xml file containing data that match the {@code classToConvert}.
     *             Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if there is an error during converting the data
     *                               into xml and writing to the file.
     */
    public static <T> void saveDataToFile(Path file, T data) throws FileNotFoundException, JAXBException {

        requireNonNull(file);
        requireNonNull(data);

        if (!Files.exists(file)) {
            throw new FileNotFoundException("File not found : " + file.toAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(data.getClass());
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(data, file.toFile());
    }

    /**
     * Load a decrypted XML file
     */
    public static Document loadDecryptedXmlFile(String xmlFile) throws
            EncryptionException {
        Document document;
        DocumentBuilderFactory builder = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = builder.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }


        return document;
    }

    /**
     * Load an encrypted XML file
     */
    public static Document loadEncryptedXmlFile(String xmlFile)
            throws EncryptionException {
        File encryptionFile = new File(xmlFile);
        Document document;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(true);

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(encryptionFile);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }


        return document;
    }

    /**
     * Save a file
     */
    public static void saveFile(Document document, String fileName) throws EncryptionException {
        File encryptionFile = new File(fileName);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(encryptionFile);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(fileOutputStream);
            transformer.transform(source, result);
            fileOutputStream.close();
        } catch (Exception e) {
            throw new EncryptionException(e);
        }
    }

    /**
     * Encrypt a file
     */
    public static Document encryptDocument(Document document, SecretKey secretKey, String algorithm)
            throws EncryptionException {
        Element rootElement = document.getDocumentElement();

        try {
            XMLCipher xmlCipher = XMLCipher.getInstance(algorithm);
            xmlCipher.init(XMLCipher.ENCRYPT_MODE, secretKey);
            xmlCipher.doFinal(document, rootElement, true);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }

        return document;
    }

    /**
     * Decrypt a file
     */
    public static Document decryptDocument(Document document, SecretKey secretKey, String algorithm)
            throws EncryptionException {
        Element encryptedDataElement = (Element) document.getElementsByTagNameNS(EncryptionConstants.EncryptionSpecNS,
                EncryptionConstants._TAG_ENCRYPTEDDATA).item(0);

        try {
            XMLCipher xmlCipher = XMLCipher.getInstance();
            xmlCipher.init(XMLCipher.DECRYPT_MODE, secretKey);
            xmlCipher.doFinal(document, encryptedDataElement);
        } catch (Exception e) {
            throw new EncryptionException(e);
        }

        return document;
    }
}
