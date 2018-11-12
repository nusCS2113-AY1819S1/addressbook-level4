package seedu.address.logic.parser.exceptions;


//@@author: IcedCoffeeBoy

/**
 * Represents a invalid privilege error when the login privilege
 **/
public class InvalidPrivilegeException extends ParseException {

    private static final String MESSAGE_INVALID_LOGIN = "Insufficient privilege"
            + "\nCurrent login as %s"
            + "\nPlease login account with higher privilege to use this command";

    public InvalidPrivilegeException(String identity) {
        super(String.format(MESSAGE_INVALID_LOGIN, identity));
    }

}
