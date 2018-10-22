package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.recruit.logic.parser.ParserUtil.parseIndexSet;

import java.util.Set;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.DeleteCandidateCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCandidateCommand object
 */
public class DeleteCandidateCommandParser implements Parser<DeleteCandidateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCandidateCommand
     * and returns an DeleteCandidateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCandidateCommand parse(String args) throws ParseException {
        String[] indexes = args.split(PREFIX_INDEX.toString());
        try {
            Set<Index> indexSet = parseIndexSet(indexes);
            return new DeleteCandidateCommand(indexSet);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCandidateCommand.MESSAGE_USAGE), pe);
        }
    }

}
