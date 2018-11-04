package seedu.address.security;

/**
 * Represents the unauthenticated {@link Command} call.
 */
public class SecurityAuthenticationException extends Exception {
    public SecurityAuthenticationException() {
        super("User is not authenticated");
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public SecurityAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
