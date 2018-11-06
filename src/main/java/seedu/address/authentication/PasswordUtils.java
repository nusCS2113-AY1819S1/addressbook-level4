package seedu.address.authentication;
//@@author liu-tianhang
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
     * Return {@code hashedPassword} after {@code password} is hashed with
     *  {@code salt}
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
     * Return {@code hashedPassword} base on {@code password}
     */
    public static String generateSecurePassword(String password) {
        String hashedPassword = null;
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        hashedPassword = Base64.getEncoder().encodeToString(securePassword);

        return hashedPassword;
    }

    /**
     * Returns true of providedPassword is equal to securedPassword after hashing.
     */
    public static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword) {
        boolean returnValue = false;

        String newSecurePassword = generateSecurePassword(providedPassword);

        returnValue = newSecurePassword.equals(securedPassword);

        return returnValue;
    }
}

