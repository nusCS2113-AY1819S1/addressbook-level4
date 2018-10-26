package seedu.address.authentication;
//@@author tianhang
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * A container for password utilities
 * code learn from http://www.appsdeveloperblog.com/encrypt-user-password-example-java/
 */
public class PasswordUtils {
    //fixed salt for the seek for cs2113, can made dynamic when needed
    private static final String salt = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;


    /**
     * It hash password
     * @param password
     * @param salt complexity of hashed password
     * @return
     */
    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }

    /**
     *
     * @param password
     * @return hashed password
     */
    public static String generateSecurePassword(String password) {
        String returnValue = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        returnValue = Base64.getEncoder().encodeToString(securePassword);

        return returnValue;
    }

    /**
     * Verify password Input
     * @param providedPassword
     * @param securedPassword
     * @return
     */
    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword) {
        boolean returnValue = false;

        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword);

        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }
}

