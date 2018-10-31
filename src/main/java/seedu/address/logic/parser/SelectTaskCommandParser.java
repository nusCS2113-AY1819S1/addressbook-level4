package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author luhan02
/**
 * Parses input arguments and creates a new SelectTaskCommand object
 */
public class SelectTaskCommandParser implements Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectTaskCommand
     * and returns an SelectTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectTaskCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectTaskCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE), pe);
        }
    }
}
