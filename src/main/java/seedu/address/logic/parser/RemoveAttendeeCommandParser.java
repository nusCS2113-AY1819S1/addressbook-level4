package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveAttendeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.user.Username;

/**
 * Parses input arguments and creates a new RemoveAttendeeCommand object
 */
public class RemoveAttendeeCommandParser implements Parser<RemoveAttendeeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveAttendeeCommand
     * and returns an RemoveAttendeeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveAttendeeCommand parse(String args) throws ParseException {
        String[] argsSplit = args.split("\\s+", 3);

        // Check if index and username are provided
        if (argsSplit.length < 3) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveAttendeeCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argsSplit[1]);
        Username username = ParserUtil.parseUsername(argsSplit[2]);
        return new RemoveAttendeeCommand(index, username);
    }
}
