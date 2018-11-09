package seedu.address.logic.parser;

import seedu.address.logic.commands.LogoutCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author: IcedCoffeeBoy

/**
 * Parses user input.
 */
public class LogoutCommandParser implements Parser<LogoutCommand> {
    private static final String MESSAGE_INVALID_LOGOUT = "Invalid logout!"
            + "\nlogout should not have any params"
            + "\nExample: logout";

    /**
     * Parses user input into command for execution.
     *
     * @param args no params expected
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public LogoutCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new LogoutCommand();
        } else {
            throw new ParseException(MESSAGE_INVALID_LOGOUT);
        }
    }
}
