package seedu.address.logic.parser;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class LoginCommandParser {
    public static final String KEY_MANAGER = "manager";
    public static final String KEY_EMPLOYEE = "employee";

    private static final String MESSAGE_INVALID_LOGIN = "Login identity should be either the following:"
            + "\nmanager\nemployee"
            + "\nExample: login manager";


    /**
     * Parses user input into command for execution.
     *
     * @param args full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        switch (trimmedArgs) {

        case KEY_MANAGER:
            return new LoginCommand(KEY_MANAGER);

        case KEY_EMPLOYEE:
            return new LoginCommand(KEY_EMPLOYEE);

        default:
            throw new ParseException(MESSAGE_INVALID_LOGIN);
        }
    }

}
