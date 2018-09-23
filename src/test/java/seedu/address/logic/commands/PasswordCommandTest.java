package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import seedu.address.commons.util.FileEncryptor;

/**
 * Testing for password encrypytion and decryption
 */
public class PasswordCommandTest {

    private String password = "test1234";
    private String tempFileName = "test";
    private String tempFileSuffix = ".tmp";
    private String toWrite = "Hello";

    /**
     * Encryption and decryption test command
     */
    @Test
    public void encryptDecryptTest () {
        try {
            File tmpFile = new File(tempFileName, tempFileSuffix);
            FileWriter writer = new FileWriter(tmpFile);
            writer.write(toWrite);
            writer.close();

            FileEncryptor feEncrypt = new FileEncryptor(password, tempFileName + tempFileSuffix);
            assertEquals("File encrypted!", feEncrypt.getMessage());


            FileEncryptor feDecrypt = new FileEncryptor(password, tempFileName + tempFileSuffix);
            assertEquals("File decrypted!", feDecrypt.getMessage());


            BufferedReader reader = new BufferedReader(new FileReader(tempFileName + tempFileSuffix));
            assertEquals(toWrite, reader.readLine());
            reader.close();

            tmpFile.delete(); // Delete and end off
        } catch (IOException ioe) {

        }
    }
}
