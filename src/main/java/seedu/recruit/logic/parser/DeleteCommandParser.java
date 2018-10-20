package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.DeleteCandidateCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCandidateCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCandidateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCandidateCommand
     * and returns an DeleteCandidateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCandidateCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCandidateCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCandidateCommand.MESSAGE_USAGE), pe);
        }
    }

}
