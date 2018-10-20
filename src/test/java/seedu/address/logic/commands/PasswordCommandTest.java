//@@author lws803
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.util.FileEncryptor;

/**
 * Testing for password encrypytion and decryption
 */
public class PasswordCommandTest {

    private String password = "test1234";
    private String tempFileName = "test.tmp";
    private String toWrite = "Hello";

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
        feEncrypt.process(password);
        assertEquals("File encrypted!", feEncrypt.getMessage());

        FileEncryptor feDecrypt = new FileEncryptor(tempFileName);
        feDecrypt.process(password);
        assertEquals("File decrypted!", feDecrypt.getMessage());

        BufferedReader reader = new BufferedReader(new FileReader(tempFileName));
        assertEquals(toWrite, reader.readLine());
        reader.close();

        String expectedMessage =
                String.format("");


    }


    @After
    public void cleanup () {
        File tmpFile = new File(tempFileName);
        tmpFile.delete(); // Delete and end off
    }


}
