package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.LoginCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses the arguments of tag command and returns as a Tag object
 */
public class LoginCommandParser implements Parser<LoginCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LoginCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String[] data = trimmedArgs.split("\\s+");

        if (data.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));
        }

        String username = data[0];
        String password = data[1];

        return new LoginCommand(username, password);
    }

}
