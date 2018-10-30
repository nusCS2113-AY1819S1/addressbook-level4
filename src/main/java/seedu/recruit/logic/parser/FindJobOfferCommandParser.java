package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import java.util.HashMap;
import java.util.List;

import seedu.recruit.logic.commands.FindJobOfferCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.joboffer.JobOfferContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindJobOfferCommand object
 */
public class FindJobOfferCommandParser implements Parser<FindJobOfferCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindJobOfferCommand
     * and returns an FindJobCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindJobOfferCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_JOB, PREFIX_GENDER,
                        PREFIX_SALARY, PREFIX_AGE_RANGE, PREFIX_EDUCATION);

        HashMap<String, List<String>> keywordsList = new HashMap<>();
        if (argMultimap.getValue(PREFIX_COMPANY_NAME).isPresent()) {
            keywordsList.put("CompanyName", (argMultimap.getAllValues(PREFIX_COMPANY_NAME)));
        }
        if (argMultimap.getValue(PREFIX_JOB).isPresent()) {
            keywordsList.put("Job", (argMultimap.getAllValues(PREFIX_JOB)));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            keywordsList.put("Gender", (argMultimap.getAllValues(PREFIX_GENDER)));
        }
        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            keywordsList.put("Salary", (argMultimap.getAllValues(PREFIX_SALARY)));
        }
        if (argMultimap.getValue(PREFIX_AGE_RANGE).isPresent()) {
            keywordsList.put("Age Range", (argMultimap.getAllValues(PREFIX_AGE_RANGE)));
        }
        if (argMultimap.getValue(PREFIX_EDUCATION).isPresent()) {
            keywordsList.put("Education", (argMultimap.getAllValues(PREFIX_EDUCATION)));
        }

        if (keywordsList.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindJobOfferCommand.MESSAGE_USAGE));
        }

        return new FindJobOfferCommand(new JobOfferContainsKeywordsPredicate(keywordsList));
    }
}
