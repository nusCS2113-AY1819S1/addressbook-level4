package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FriendCommand;
import seedu.address.logic.commands.UnfriendCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input and creates a new UnfriendCommand
 */
public class UnfriendCommandParser implements Parser<UnfriendCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnfriendCommand
     * and returns an UnfriendCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnfriendCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new UnfriendCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfriendCommand.MESSAGE_USAGE), pe);
        }
    }
}
