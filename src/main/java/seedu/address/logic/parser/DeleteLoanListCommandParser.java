package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLoanListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DeleteLoanListCommandParser implements Parser<DeleteLoanListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteLoanListCommand
     * and returns an DeleteLoanListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLoanListCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteLoanListCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLoanListCommand.MESSAGE_USAGE), pe);
        }
    }
}
