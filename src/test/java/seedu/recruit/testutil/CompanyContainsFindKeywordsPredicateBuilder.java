package seedu.recruit.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.HashMap;
import java.util.List;

import seedu.recruit.logic.parser.ArgumentMultimap;
import seedu.recruit.logic.parser.ArgumentTokenizer;
import seedu.recruit.model.company.CompanyContainsFindKeywordsPredicate;

/**
 * A utility class that helps with building predicate for {@Code CompanyContainsFindKeywordsPredicate}
 * from a string of input arguments
 */

public class CompanyContainsFindKeywordsPredicateBuilder {

    public static final String KEY_NAME = "CompanyName";
    public static final String KEY_PHONE = "Phone";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_ADDRESS = "Address";


    private HashMap<String, List<String>> keywordsList = new HashMap<>();
    private CompanyContainsFindKeywordsPredicate companyPredicate;

    public CompanyContainsFindKeywordsPredicateBuilder(String userInput) {
        this.companyPredicate = preparePredicate(userInput);
    }

    public CompanyContainsFindKeywordsPredicate getCompanyPredicate() {
        return companyPredicate;
    }

    public HashMap<String, List<String>> getKeywordsList() {
        return keywordsList;
    }

    /**
     * Parses the @param userInput and
     * @return CompanyContainsFindKeywordsPredicate as a predicate
     */
    public CompanyContainsFindKeywordsPredicate preparePredicate (String userInput) {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_COMPANY_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        //HashMap<String,List<String>> keywordsList = new HashMap<>();

        if (argMultimap.getValue(PREFIX_COMPANY_NAME).isPresent()) {
            this.keywordsList.put(KEY_NAME, (argMultimap.getAllValues(PREFIX_COMPANY_NAME)));
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

        System.out.println(keywordsList);

        return new CompanyContainsFindKeywordsPredicate(keywordsList);
    }
}
