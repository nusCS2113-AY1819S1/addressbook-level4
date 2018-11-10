package seedu.recruit.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_AGE_RANGE;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EDUCATION;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_SALARY;


import java.util.HashMap;
import java.util.List;

import seedu.recruit.logic.parser.ArgumentMultimap;
import seedu.recruit.logic.parser.ArgumentTokenizer;
import seedu.recruit.model.joboffer.JobOfferContainsFindKeywordsPredicate;

/**
 * A utility class that helps with building predicate for {@Code JobOfferContainsFindKeywordsPredicate}
 * from a string of input arguments
 */

public class JobOfferContainsFindKeywordsPredicateBuilder {

    public static final String KEY_NAME = "CompanyName";
    public static final String KEY_JOB = "Job";
    public static final String KEY_AGE_RANGE = "Age Range";
    public static final String KEY_EDUCATION = "Education";
    public static final String KEY_SALARY = "Salary";
    public static final String KEY_GENDER = "Gender";


    private HashMap<String, List<String>> keywordsList = new HashMap<>();
    private JobOfferContainsFindKeywordsPredicate jobOfferPredicate;

    public JobOfferContainsFindKeywordsPredicateBuilder(String userInput) {
        this.jobOfferPredicate = preparePredicate(userInput);
    }

    public JobOfferContainsFindKeywordsPredicate getJobOfferPredicate() {
        return jobOfferPredicate;
    }

    public HashMap<String, List<String>> getKeywordsList() {
        return keywordsList;
    }

    /**
     * Parses the @param userInput and
     * @return JobOfferContainsFindKeywordsPredicate as a predicate
     */
    public JobOfferContainsFindKeywordsPredicate preparePredicate (String userInput) {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_COMPANY_NAME, PREFIX_AGE_RANGE, PREFIX_JOB, PREFIX_GENDER,
                        PREFIX_EDUCATION, PREFIX_SALARY);

        //HashMap<String,List<String>> keywordsList = new HashMap<>();

        if (argMultimap.getValue(PREFIX_COMPANY_NAME).isPresent()) {
            this.keywordsList.put(KEY_NAME, (argMultimap.getAllValues(PREFIX_COMPANY_NAME)));
        }
        if (argMultimap.getValue(PREFIX_AGE_RANGE).isPresent()) {
            this.keywordsList.put(KEY_AGE_RANGE, (argMultimap.getAllValues(PREFIX_AGE_RANGE)));
        }
        if (argMultimap.getValue(PREFIX_JOB).isPresent()) {
            this.keywordsList.put(KEY_JOB, (argMultimap.getAllValues(PREFIX_JOB)));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            this.keywordsList.put(KEY_GENDER, (argMultimap.getAllValues(PREFIX_GENDER)));
        }
        if (argMultimap.getValue(PREFIX_EDUCATION).isPresent()) {
            this.keywordsList.put(KEY_EDUCATION, (argMultimap.getAllValues(PREFIX_EDUCATION)));
        }
        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            this.keywordsList.put(KEY_SALARY, (argMultimap.getAllValues(PREFIX_SALARY)));
        }

        System.out.println(keywordsList);

        return new JobOfferContainsFindKeywordsPredicate(keywordsList);
    }
}
