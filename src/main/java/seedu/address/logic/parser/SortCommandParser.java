package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    private final String KEY_NAME = "name";
    private final String KEY_STARTTIME = "starttime";
    private final String KEY_ENDTIME = "endtime";


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
        case (KEY_NAME):
            key = KEY_NAME;
            break;
        case (KEY_STARTTIME):
            key = KEY_STARTTIME;
            break;
        case (KEY_ENDTIME):
            key = KEY_ENDTIME;
            break;
        default:
             throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return new SortCommand(key);
    }
}
