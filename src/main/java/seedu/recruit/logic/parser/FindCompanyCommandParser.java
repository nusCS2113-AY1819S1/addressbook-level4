package seedu.recruit.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.recruit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.recruit.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.HashMap;
import java.util.List;

import seedu.recruit.logic.commands.FindCompanyCommand;
import seedu.recruit.logic.parser.exceptions.ParseException;
import seedu.recruit.model.company.CompanyNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCompanyCommand object
 */
public class FindCompanyCommandParser implements Parser<FindCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCompanyCommand
     * and returns an FindCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCompanyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS);

        HashMap<String, List<String>> keywordsList = new HashMap<>();
        if (argMultimap.getValue(PREFIX_COMPANY_NAME).isPresent()) {
            keywordsList.put("CompanyName", (argMultimap.getAllValues(PREFIX_COMPANY_NAME)));
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

        if (keywordsList.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCompanyCommand.MESSAGE_USAGE));
        }

        return new FindCompanyCommand(new CompanyNameContainsKeywordsPredicate(keywordsList));
    }

}
