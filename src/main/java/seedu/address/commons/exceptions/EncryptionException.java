package seedu.address.commons.exceptions;

/**
 * Throws an exception when encryption operations fail
 */
public class EncryptionException extends Exception {
    public EncryptionException(Exception cause) {
        super(cause);
    }
}
