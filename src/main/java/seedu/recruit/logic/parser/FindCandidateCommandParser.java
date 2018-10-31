package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashMap;
import java.util.List;

import seedu.recruit.logic.commands.FindCandidateCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.candidate.CandidateContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCandidateCommand object
 */
public class FindCandidateCommandParser implements Parser<FindCandidateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCandidateCommand
     * and returns an FindCandidateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCandidateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GENDER, PREFIX_AGE, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS, PREFIX_JOB, PREFIX_EDUCATION, PREFIX_SALARY, PREFIX_TAG);

        HashMap<String, List<String>> keywordsList = new HashMap<>();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            keywordsList.put("Name", (argMultimap.getAllValues(PREFIX_NAME)));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            keywordsList.put("Gender", (argMultimap.getAllValues(PREFIX_GENDER)));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            keywordsList.put("Age", (argMultimap.getAllValues(PREFIX_AGE)));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            keywordsList.put("Phone", (argMultimap.getAllValues(PREFIX_PHONE)));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            keywordsList.put("Email", (argMultimap.getAllValues(PREFIX_EMAIL)));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            keywordsList.put("Address", (argMultimap.getAllValues(PREFIX_ADDRESS)));
        }
        if (argMultimap.getValue(PREFIX_JOB).isPresent()) {
            keywordsList.put("Job", (argMultimap.getAllValues(PREFIX_JOB)));
        }
        if (argMultimap.getValue(PREFIX_EDUCATION).isPresent()) {
            keywordsList.put("Education", (argMultimap.getAllValues(PREFIX_EDUCATION)));
        }
        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            keywordsList.put("Salary", (argMultimap.getAllValues(PREFIX_SALARY)));
        }

        if (keywordsList.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCandidateCommand.MESSAGE_USAGE));
        }

        return new FindCandidateCommand(new CandidateContainsKeywordsPredicate(keywordsList));
    }
}
