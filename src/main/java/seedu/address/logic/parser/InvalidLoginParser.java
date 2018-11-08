package seedu.address.logic.parser;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author: IcedCoffeeBoy

/**
 * Parses user input.
 */
public class InvalidLoginParser implements Parser<LoginCommand> {

    private static final String MESSAGE_INVALID_LOGIN = "User already login as %s"
            + "\nPlease logout to login again"
            + "\nExample: logout";

    /**
     * Parses user input into command for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String identity) throws ParseException {
        throw new ParseException(String.format(MESSAGE_INVALID_LOGIN, identity));
    }
}


