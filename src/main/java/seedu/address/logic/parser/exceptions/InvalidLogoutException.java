package seedu.address.logic.parser.exceptions;

//@@author: IcedCoffeeBoy

/**
 * Represents a invalid logout error when no user has login
 */
public class InvalidLogoutException extends ParseException {

    private static final String MESSAGE_INVALID_LOGOUT = "No user currently login. Current privilege is %s"
            + "\nTo login please use the login command"
            + "\nExample: login manager";

    public InvalidLogoutException(String identity) {
        super(String.format(MESSAGE_INVALID_LOGOUT, identity));
    }

}
