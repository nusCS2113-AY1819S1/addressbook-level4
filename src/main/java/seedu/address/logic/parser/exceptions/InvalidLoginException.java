package seedu.address.logic.parser.exceptions;

/**
 * Represents a parse error encountered by a parser.
 */
public class InvalidLoginException extends ParseException {

    private static final String MESSAGE_INVALID_LOGIN = "User already login as %s"
            + "\nPlease logout to login again"
            + "\nExample: logout";

    public InvalidLoginException(String identity) {
        super(String.format(MESSAGE_INVALID_LOGIN, identity));
    }

}
