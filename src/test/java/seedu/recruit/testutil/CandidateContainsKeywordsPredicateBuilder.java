package seedu.recruit.testutil;

import static java.util.Objects.requireNonNull;
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

import seedu.recruit.logic.parser.ArgumentMultimap;
import seedu.recruit.logic.parser.ArgumentTokenizer;
import seedu.recruit.model.candidate.CandidateContainsKeywordsPredicate;

/**
 * A utility class that helps with building predicate for {@Code CandidateContainsKeywordsPredicate}
 * from a string of input arguments
 */

public class CandidateContainsKeywordsPredicateBuilder {

    public static final String KEY_NAME = "Name";
    public static final String KEY_GENDER = "Gender";
    public static final String KEY_AGE = "Age";
    public static final String KEY_PHONE = "Phone";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_JOB = "Job";
    public static final String KEY_EDUCATION = "Education";
    public static final String KEY_SALARY = "Salary";


    private HashMap<String, List<String>> keywordsList = new HashMap<>();
    private CandidateContainsKeywordsPredicate candidatePredicate;

    public CandidateContainsKeywordsPredicateBuilder (String userInput) {
        this.candidatePredicate = preparePredicate(userInput);
    }

    public CandidateContainsKeywordsPredicate getCandidatePredicate() {
        return candidatePredicate;
    }

    public HashMap<String, List<String>> getKeywordsList() {
        return keywordsList;
    }

    /**
     * Parses the @param userInput and
     * @return CandidateContainsKeywordsPredicate as a predicate
     */
    public CandidateContainsKeywordsPredicate preparePredicate (String userInput) {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_NAME, PREFIX_GENDER, PREFIX_AGE, PREFIX_PHONE,
                        PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_JOB, PREFIX_EDUCATION, PREFIX_SALARY, PREFIX_TAG);

        //HashMap<String,List<String>> keywordsList = new HashMap<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            this.keywordsList.put(KEY_NAME, (argMultimap.getAllValues(PREFIX_NAME)));
        }
        if (argMultimap.getValue(PREFIX_GENDER).isPresent()) {
            this.keywordsList.put(KEY_GENDER, (argMultimap.getAllValues(PREFIX_GENDER)));
        }
        if (argMultimap.getValue(PREFIX_AGE).isPresent()) {
            this.keywordsList.put(KEY_AGE, (argMultimap.getAllValues(PREFIX_AGE)));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            this.keywordsList.put(KEY_PHONE, (argMultimap.getAllValues(PREFIX_PHONE)));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            this.keywordsList.put(KEY_EMAIL, (argMultimap.getAllValues(PREFIX_EMAIL)));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            this.keywordsList.put(KEY_ADDRESS, (argMultimap.getAllValues(PREFIX_ADDRESS)));
        }
        if (argMultimap.getValue(PREFIX_JOB).isPresent()) {
            this.keywordsList.put(KEY_JOB, (argMultimap.getAllValues(PREFIX_JOB)));
        }
        if (argMultimap.getValue(PREFIX_EDUCATION).isPresent()) {
            this.keywordsList.put(KEY_EDUCATION, (argMultimap.getAllValues(PREFIX_EDUCATION)));
        }
        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            this.keywordsList.put(KEY_SALARY, (argMultimap.getAllValues(PREFIX_SALARY)));
        }

        System.out.println(keywordsList);

        return new CandidateContainsKeywordsPredicate(keywordsList);
    }
}
