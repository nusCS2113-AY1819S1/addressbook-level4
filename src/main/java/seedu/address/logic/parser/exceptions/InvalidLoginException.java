package seedu.address.logic.parser.exceptions;

//@@author: IcedCoffeeBoy

/**
 * Represents a invalid login error when user is already login
 */
public class InvalidLoginException extends ParseException {

    private static final String MESSAGE_INVALID_LOGIN = "User already login as %s"
            + "\nPlease logout to login again"
            + "\nExample: logout";

    public InvalidLoginException(String identity) {
        super(String.format(MESSAGE_INVALID_LOGIN, identity));
    }

}
