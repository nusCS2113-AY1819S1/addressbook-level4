package seedu.recruit.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;

import java.util.HashMap;
import java.util.List;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.logic.commands.FilterCandidateCommand;
import seedu.recruit.logic.parser.ArgumentMultimap;
import seedu.recruit.logic.parser.ArgumentTokenizer;
import seedu.recruit.logic.parser.exceptions.ParseException;

import seedu.recruit.model.joboffer.JobOfferContainsFilterKeywordsPredicate;

/**
 * A utility class that helps with building predicate for {@Code JobOfferContainsFilterKeywordsPredicate}
 * from a string of input arguments
 */

public class JobOfferContainsFilterKeywordsPredicateBuilder {

    public static final String KEY_NAME = "CompanyName";
    public static final String KEY_JOB = "Job";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_SALARY = "Salary";
    public static final String KEY_AGE = "Age";
    public static final String KEY_EDUCATION = "Education";

    private HashMap<String, List<String>> keywordsList = new HashMap<>();
    private JobOfferContainsFilterKeywordsPredicate jobOfferPredicate;

    public JobOfferContainsFilterKeywordsPredicateBuilder(String userInput) throws ParseException {
        this.jobOfferPredicate = preparePredicate(userInput);
    }

    public JobOfferContainsFilterKeywordsPredicate getJobOfferPredicate() {
        return jobOfferPredicate;
    }

    public HashMap<String, List<String>> getKeywordsList() {
        return keywordsList;
    }

    /**
     * Parses the @param userInput and
     * @return CompanyContainsFilterKeywordsPredicate as a predicate
     */
    public JobOfferContainsFilterKeywordsPredicate preparePredicate (String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_COMPANY_NAME, PREFIX_JOB, PREFIX_GENDER, PREFIX_SALARY,
                        PREFIX_AGE, PREFIX_EDUCATION);

        if (argMultimap.getValue(PREFIX_COMPANY_NAME).isPresent()) {
            this.keywordsList.put(KEY_NAME, (argMultimap.getAllValues(PREFIX_COMPANY_NAME)));
        }
        if (argMultimap.getValue(PREFIX_JOB).isPresent()) {
            this.keywordsList.put(KEY_JOB, (argMultimap.getAllValues(PREFIX_JOB)));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            this.keywordsList.put(KEY_GENDER, (argMultimap.getAllValues(PREFIX_GENDER)));
        }
        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            this.keywordsList.put(KEY_SALARY, (argMultimap.getAllValues(PREFIX_SALARY)));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            this.keywordsList.put(KEY_AGE, (argMultimap.getAllValues(PREFIX_AGE)));
        }
        if (argMultimap.getValue(PREFIX_EDUCATION).isPresent()) {
            this.keywordsList.put(KEY_EDUCATION, (argMultimap.getAllValues(PREFIX_EDUCATION)));
        }

        if (keywordsList.isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterCandidateCommand.MESSAGE_USAGE));
        }

        return new JobOfferContainsFilterKeywordsPredicate(keywordsList);
    }
}
