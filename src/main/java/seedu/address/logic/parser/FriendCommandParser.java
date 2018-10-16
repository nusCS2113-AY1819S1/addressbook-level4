package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FriendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments and creates a new FriendCommand
 */
public class FriendCommandParser implements Parser<FriendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FriendCommand
     * and returns an FriendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FriendCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FriendCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FriendCommand.MESSAGE_USAGE), pe);
        }
    }

}
