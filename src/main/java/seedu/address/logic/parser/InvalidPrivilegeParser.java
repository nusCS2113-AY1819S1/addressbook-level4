package seedu.address.logic.parser;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author: IcedCoffeeBoy

/**
 * Parses user input.
 */
public class InvalidPrivilegeParser implements Parser<LoginCommand> {

    private static final String MESSAGE_INVALID_LOGIN = "Insufficient privilege"
            + "\nCurrent login as %s"
            + "\nPlease login account with higher privilege to use this command";

    /**
     * Parses user input into command for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String identity) throws ParseException {
        throw new ParseException(String.format(MESSAGE_INVALID_LOGIN, identity));
    }
}


