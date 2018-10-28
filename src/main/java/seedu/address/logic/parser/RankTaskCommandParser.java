package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RankTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RankTaskCommand object
 */
public class RankTaskCommandParser implements Parser<RankTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RankTaskCommand
     * and returns an RankTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RankTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String filter = args.trim();

        if ("module".equals(filter) || "date".equals(filter) || "priority".equals(filter)) {
            return new RankTaskCommand(filter);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RankTaskCommand.MESSAGE_USAGE));
    }

}
