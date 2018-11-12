package seedu.address.logic.parser;

import seedu.address.logic.commands.ShowMineCommand;
import seedu.address.logic.parser.exceptions.ParseException;


//@@author: IcedCoffeeBoy

/**
 * Parses input arguments and creates a new ShowMineCommand object
 */
public class ShowMineCommandParser implements Parser<ShowMineCommand> {

    public static final String MESSAGE_INVALID_LOGIN_USER = "Please login with email to use this function"
            + "\nExample: login as hello@gmail.com";

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an ShowMineCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowMineCommand parse(String args) throws ParseException {
        if (args == null) {
            throw new ParseException(MESSAGE_INVALID_LOGIN_USER);
        }
        return new ShowMineCommand(args);
    }
}
