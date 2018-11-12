package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveExpensesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveExpensesCommand object
 */
public class RemoveExpensesCommandParser implements Parser<RemoveExpensesCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveExpensesCommand
     * and returns an RemoveExpensesCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveExpensesCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new RemoveExpensesCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveExpensesCommand.MESSAGE_USAGE), pe);
        }
    }

}
