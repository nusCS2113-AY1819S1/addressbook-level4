package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.BlacklistCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new BlacklistCommand object
 */
public class BlacklistCommandParser implements Parser<BlacklistCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BlacklistCommand
     * and returns a BlacklistCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BlacklistCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Index index = ParserUtil.parseIndex(args);
            return new BlacklistCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, BlacklistCommand.MESSAGE_USAGE),pe);
        }
    }
}

