//@@author XiaoYunhan
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortTaskCommand object
 */
public class SortTaskCommandParser implements Parser<SortTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortTaskCommand
     * and returns an SortTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String filter = args.trim();

        if ("module".equals(filter) || "date".equals(filter) || "priority".equals(filter) || "default".equals(filter)) {
            return new SortTaskCommand(filter);
        } else if ("reverse".equals(filter)) {
            return new SortTaskCommand(filter);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortTaskCommand.MESSAGE_USAGE));
    }

}
