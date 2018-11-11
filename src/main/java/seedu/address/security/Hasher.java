package seedu.address.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Implements hashing methods
 * (Ref: https://stackoverflow.com/questions/33085493/how-to-hash-a-password-with-sha-512-in-java)
 */
public class Hasher {

    public static final String mySalt = "Akshay";

    /**
     *
     * @param passwordToHash input password
     * @param salt
     * @param algo predefined algo such as SHA-256, or SHA-512 etc
     * @return hashedPassword to compare
     */
    public static String hashPassword(String passwordToHash, String salt, String algo) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algo);
            md.update(passwordToHash.getBytes(StandardCharsets.UTF_8));
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
