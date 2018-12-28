package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PREFIX;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.AddressContainsKeywordsPredicate;
import seedu.address.model.person.AllergyContainsKeywordsPredicate;
import seedu.address.model.person.BloodTypeContainsKeywordsPredicate;
import seedu.address.model.person.CountryContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.InfoContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NricContainsKeywordsPredicate;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 * Switch cases for most attributes
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        //@@author chokxy
        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (nameKeywords.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        switch(nameKeywords[0]) {
        //Personal prefixes
        case "n/":
            //create NameContainsKeywordsPredicate
            return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case "p/":
            //create PhoneContainsKeywordsPredicate
            return new FindCommand(new PhoneContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case "e/":
            //create EmailContainsKeywordsPredicate
            return new FindCommand(new EmailContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case "a/":
            //create AddressContainsKeywordsPredicate
            return new FindCommand(new AddressContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case "t/":
            //create TagContainsKeywordsPredicate
            return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case "ic/":
            //create NricContainsKeywordsPredicate
            return new FindCommand(new NricContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        //Medical Prefixes

        case "i/":
            //create InfoContainsKeywordsPredicate
            return new FindCommand(new InfoContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case "hsb/":
            //create BloodTypeContainsKeywordsPredicate
            return new FindCommand(new BloodTypeContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case "hsc/":
            //create CountryContainsKeywordsPredicate
            return new FindCommand(new CountryContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        case "hsa/":
            //create AllergyContainsKeywordsPredicate
            return new FindCommand(new AllergyContainsKeywordsPredicate(Arrays.asList(nameKeywords)));

        default:
            throw new ParseException(MESSAGE_INVALID_PREFIX);
        }
    }

}
