package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCommand
     * and returns an CreateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        String key;
        switch (trimmedArgs) {
        case (SortCommand.KEY_NAME):
            key = SortCommand.KEY_NAME;
            break;
        case (SortCommand.KEY_STARTTIME):
            key = SortCommand.KEY_STARTTIME;
            break;
        case (SortCommand.KEY_ENDTIME):
            key = SortCommand.KEY_ENDTIME;
            break;
        case (SortCommand.KEY_DATE):
            key = SortCommand.KEY_DATE;
            break;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(key);
    }
}
