//@@author arty9
package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UncompleteTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CompleteTaskCommand object
 */
public class UncompleteTaskCommandParser implements Parser<UncompleteTaskCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UncompleteTaskCommand
     * and returns an UncompleteTaskCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UncompleteTaskCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            Index index = ParserUtil.parseIndex(args);
            return new UncompleteTaskCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UncompleteTaskCommand.MESSAGE_USAGE), pe);
        }

    }
}
