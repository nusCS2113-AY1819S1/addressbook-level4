package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.recruit.commons.core.index.Index;
import seedu.recruit.logic.commands.SelectCandidateCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectCandidateCommand object
 */
public class SelectCandidateCommandParser implements Parser<SelectCandidateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCandidateCommand
     * and returns an SelectCandidateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCandidateCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectCandidateCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCandidateCommand.MESSAGE_USAGE), pe);
        }
    }
}
