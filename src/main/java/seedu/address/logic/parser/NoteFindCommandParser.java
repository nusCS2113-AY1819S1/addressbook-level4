package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE_KEY_WORD;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.NoteFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NoteFindCommand object
 */
public class NoteFindCommandParser implements Parser<NoteFindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NoteFindCommand
     * and returns a NoteFindCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public NoteFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NOTE_KEY_WORD);

        if (!arePrefixesPresent(argMultimap, PREFIX_NOTE_KEY_WORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NoteFindCommand.MESSAGE_USAGE));
        }

        String keywordInvalidPatternRegEx = ".+ +.+";

        List<String> keywordsList = new ArrayList<>();
        for (String keyword : argMultimap.getAllValues(PREFIX_NOTE_KEY_WORD)) {
            String trimmedKeyword = keyword.trim();
            if (trimmedKeyword.isEmpty() || trimmedKeyword.matches(keywordInvalidPatternRegEx)) {
                throw new ParseException(NoteFindCommand.MESSAGE_INVALID_KEYWORD);
            }
            keywordsList.add(trimmedKeyword);
        }

        return new NoteFindCommand(keywordsList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
