package seedu.address.commons.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordUtil {

    private static final int SALT_BYTE_SIZE = 16;
    private static final int HASH_BYTE_SIZE = 64 * 8;
    private static final int PKBDF2_ITERATIONS = 1000;

    /**
     * Returns the password encrypted using PBKDF2WithHmacSHA1.
     */
    public String getEncryptedPassword(String plainPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return generateHash(plainPassword);
    }

    /**
     * Generates the encrypted password using PBKDF2WithHmacSHA1.
     */
    private String generateHash(String plainPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] chars = plainPassword.toCharArray();
        byte[] salt = generateSalt();

        PBEKeySpec pbeKeySpec = new PBEKeySpec(chars, salt, PKBDF2_ITERATIONS, HASH_BYTE_SIZE);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        return PKBDF2_ITERATIONS + ":" + convertToHex(salt) + convertToHex(hash);
    }

    /**
     * Generates the salt used in password encryption.
     */
    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        secureRandom.nextBytes(salt);
        return salt;
    }

    /**
     * Converts an array of bytes to hexadecimal string.
     */
    private String convertToHex(byte[] array) {
        BigInteger bigInteger = new BigInteger(1, array);
        String hex = bigInteger.toString(SALT_BYTE_SIZE);
        int paddingLength = (array.length * 2) - hex.length();

        if (paddingLength > 0) {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }

        return hex;
    }
}
