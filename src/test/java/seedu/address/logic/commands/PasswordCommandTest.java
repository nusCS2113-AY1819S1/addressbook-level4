//@@author lws803
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.exceptions.FileEncryptorException;
import seedu.address.commons.util.FileEncryptor;

/**
 * Testing for password encrypytion and decryption
 */
public class PasswordCommandTest {

    static private final Logger LOGGER = Logger.getLogger(PasswordCommandTest.class.getName());

    private String password = "test1234";
    private String tempFileName = "test.tmp";
    private String toWrite = "Hello";

    /**
     * Sets up the temporary file
     * @throws IOException
     */
    @Before
    public void setup () throws IOException {
        File tmpFile = new File(tempFileName);
        FileWriter writer = new FileWriter(tmpFile);
        writer.write(toWrite);
        writer.close();
    }

    /**
     * Encryption and decryption test command
     */
    @Test
    public void encryptDecryptTest () throws IOException {

        FileEncryptor feEncrypt = new FileEncryptor(tempFileName);

        try {
            String message = feEncrypt.process(password);
            assertEquals(FileEncryptor.MESSAGE_ENCRYPTED, message);
        } catch (FileEncryptorException fex) {
            LOGGER.log(Level.WARNING, fex.getLocalizedMessage());
        }

        try {
            String message = feEncrypt.process(password);
            assertEquals(FileEncryptor.MESSAGE_DECRYPTED, message);
        } catch (FileEncryptorException fex) {
            LOGGER.log(Level.WARNING, fex.getLocalizedMessage());
        }

        BufferedReader reader = new BufferedReader(new FileReader(tempFileName));
        assertEquals(toWrite, reader.readLine());
        reader.close();
    }

    /**
     * Cleans up the temp file
     */
    @After
    public void cleanup () {
        File tmpFile = new File(tempFileName);
        tmpFile.delete();
    }
}
