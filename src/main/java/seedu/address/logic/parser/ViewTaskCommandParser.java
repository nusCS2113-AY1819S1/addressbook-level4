package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewTaskCommand object
 */
public class ViewTaskCommandParser implements Parser<ViewTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewTaskCommand
     * and returns an ViewTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        args = args.trim();

        if (args.equals("uncompleted") || args.equals("completed") || args.equals("all")) {
            String filter = args;
            return new ViewTaskCommand(filter);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewTaskCommand.MESSAGE_USAGE));
    }

}
