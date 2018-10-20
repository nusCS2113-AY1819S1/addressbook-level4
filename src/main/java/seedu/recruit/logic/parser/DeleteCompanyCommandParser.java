package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.DeleteCompanyCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCompanyCommand object
 */

public class DeleteCompanyCommandParser implements Parser<DeleteCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCompanyCommand
     * and returns an DeleteCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCompanyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCompanyCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCompanyCommand.MESSAGE_USAGE), pe);
        }
    }
}
