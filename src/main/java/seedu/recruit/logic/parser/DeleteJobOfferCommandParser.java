package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.DeleteJobOfferCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteJobOfferCommand object
 */

public class DeleteJobOfferCommandParser implements Parser<DeleteJobOfferCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteJobOfferCommand
     * and returns an DeleteJobOfferCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteJobOfferCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteJobOfferCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteJobOfferCommand.MESSAGE_USAGE), pe);
        }
    }

}
