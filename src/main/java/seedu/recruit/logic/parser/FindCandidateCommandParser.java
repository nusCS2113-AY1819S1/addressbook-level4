package seedu.recruit.logic.parser;

import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.candidate.AddressContainsKeywordsPredicate;
import seedu.recruit.model.candidate.EmailContainsKeywordsPredicate;
import seedu.recruit.model.candidate.NameContainsKeywordsPredicate;
import seedu.recruit.model.candidate.PhoneContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCandidateCommand object
 */
public class FindCandidateCommandParser implements Parser<FindCandidateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCandidateCommand
     * and returns an FindCandidateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCandidateCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCandidateCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");
        List<String> keywordsList = new ArrayList<String> (Arrays.asList(keywords));

        return new FindCandidateCommand(new NameContainsKeywordsPredicate(keywordsList),
                new AddressContainsKeywordsPredicate(keywordsList),
                new EmailContainsKeywordsPredicate(keywordsList),
                new PhoneContainsKeywordsPredicate(keywordsList));

    }

}
