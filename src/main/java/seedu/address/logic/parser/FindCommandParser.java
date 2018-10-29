package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(trimmedArgs, PREFIX_KEYWORD, PREFIX_NAME,
                PREFIX_CONTACT, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DATETIME, PREFIX_TAG);

        if (trimmedArgs.isEmpty() || !anyPrefixesPresent(argMultimap, PREFIX_KEYWORD, PREFIX_NAME, PREFIX_CONTACT,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DATETIME, PREFIX_TAG)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String keywords = argMultimap.getValue(PREFIX_KEYWORD).get().trim();
        String nameKeywords = argMultimap.getValue(PREFIX_NAME).get().trim();
        String contactKeywords = argMultimap.getValue(PREFIX_CONTACT).get().trim();
        String emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().trim();
        String phoneKeywords = argMultimap.getValue(PREFIX_PHONE).get().trim();
        String addressKeywords = argMultimap.getValue(PREFIX_ADDRESS).get().trim();
        String datetimeKeywords = argMultimap.getValue(PREFIX_DATETIME).get().trim();
        String tagKeywords = argMultimap.getValue(PREFIX_TAG).get();

        String[] keywordsArray = keywords.split("\\s+");
        String[] nameKeywordsArray = nameKeywords.split("\\s+");
        String[] contactKeywordsArray = contactKeywords.split("\\s+");
        String[] emailKeywordsArray = emailKeywords.split("\\s+");
        String[] phoneKeywordsArray = phoneKeywords.split("\\s+");
        String[] addressKeywordsArray = addressKeywords.split("\\s+");
        String[] datetimeKeywordsArray = datetimeKeywords.split("\\s+");
        String[] tagKeywordsArray = tagKeywords.split("\\s+");


        //TODO: Deal with optional value
        return new FindCommand(
                new EventContainsKeywordsPredicate(Arrays.asList(keywordsArray), Arrays.asList(nameKeywordsArray),
                        Arrays.asList(contactKeywordsArray), Arrays.asList(emailKeywordsArray),
                        Arrays.asList(phoneKeywordsArray), Arrays.asList(addressKeywordsArray),
                        Arrays.asList(datetimeKeywordsArray), Arrays.asList(tagKeywordsArray)));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
