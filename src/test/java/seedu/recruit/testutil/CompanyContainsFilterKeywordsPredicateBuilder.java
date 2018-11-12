package seedu.recruit.testutil;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.HashMap;
import java.util.List;

import seedu.recruit.commons.core.Messages;
import seedu.recruit.logic.commands.FilterCandidateCommand;
import seedu.recruit.logic.parser.ArgumentMultimap;
import seedu.recruit.logic.parser.ArgumentTokenizer;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.company.CompanyContainsFilterKeywordsPredicate;

/**
 * A utility class that helps with building predicate for {@Code CompanyContainsFilterKeywordsPredicate}
 * from a string of input arguments
 */

public class CompanyContainsFilterKeywordsPredicateBuilder {

    public static final String KEY_NAME = "CompanyName";
    public static final String KEY_PHONE = "Phone";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_ADDRESS = "Address";


    private HashMap<String, List<String>> keywordsList = new HashMap<>();
    private CompanyContainsFilterKeywordsPredicate companyPredicate;

    public CompanyContainsFilterKeywordsPredicateBuilder(String userInput) throws ParseException {
        this.companyPredicate = preparePredicate(userInput);
    }

    public CompanyContainsFilterKeywordsPredicate getCompanyPredicate() {
        return companyPredicate;
    }

    public HashMap<String, List<String>> getKeywordsList() {
        return keywordsList;
    }

    /**
     * Parses the @param userInput and
     * @return CompanyContainsFilterKeywordsPredicate as a predicate
     */
    public CompanyContainsFilterKeywordsPredicate preparePredicate (String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_COMPANY_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

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

        if (keywordsList.isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    FilterCandidateCommand.MESSAGE_USAGE));
        }

        return new CompanyContainsFilterKeywordsPredicate(keywordsList);
    }
}
