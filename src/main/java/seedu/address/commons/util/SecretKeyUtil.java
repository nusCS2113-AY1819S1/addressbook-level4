package seedu.address.commons.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.xml.security.utils.JavaUtils;


/**
 * Creates a secret key from String and vice-versa
 */
public class SecretKeyUtil {

    /**
     * Creates a secret key
     * @param algorithm used to generate the secret key from {@code KeyGenerator}
     * @return An instance of {@code SecretKey} generated
     */
    public static SecretKey getSecretKey(String algorithm) {
        KeyGenerator keyGenerator = null;

        try {
            keyGenerator = KeyGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return keyGenerator.generateKey();
    }

    /**
     * Converts a {@code SecretKey} into {@code file}
     * @param secretKey to be converted
     * @param fileName used to save the {@code SecretKey}
     * @throws Exception if the {@code SecretKey} cannot be encoded
     */
    public static void saveSecretKey(SecretKey secretKey, String fileName) throws IOException {
        byte[] keyByte = secretKey.getEncoded();
        FileOutputStream outputStream = new FileOutputStream(fileName);
        outputStream.write(keyByte);
    }

    /**
     * Converts a {@code File} into {@code SecretKey}
     * @param fileName of the file containing the {@code SecretKey}
     * @return the converted {@code SecretKey}
     * @throws Exception if the file does not exist
     */
    public static SecretKey readSecretKey(String fileName) throws IOException {
        SecretKey secretKey = new SecretKeySpec(JavaUtils.getBytesFromFile(fileName), "AES");
        return secretKey;
    }
}
