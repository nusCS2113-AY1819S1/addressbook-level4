package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteDistributorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteProductCommand object
 */
public class DeleteDistributorCommandParser implements Parser<DeleteDistributorCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProductCommand
     * and returns an DeleteProductCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteDistributorCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteDistributorCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteDistributorCommand.MESSAGE_USAGE), pe);
        }
    }

}
