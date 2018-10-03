package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RegisterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RegisterCommand object
 */
public class RegisterCommandParser implements Parser<RegisterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RegisterCommand
     * and returns an RegisterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RegisterCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RegisterCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE), pe);
        }
    }
}
