package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.DeleteCompanyCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteCompanyCommand object
 */

public class DeleteCompanyCommandParser implements Parser<DeleteCompanyCommand> {
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
