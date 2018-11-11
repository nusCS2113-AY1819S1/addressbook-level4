package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.tag.Tag.MESSAGE_TAG_CONSTRAINTS;

import java.util.stream.Stream;

import seedu.address.logic.commands.SelectTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

//@@author ChanChunCheong
/**
 * Parses input arguments and creates a new AddTagCommand object
 */

public class SelectTagCommandParser implements Parser<SelectTagCommand> {
    public static final String TAG_VALIDATION_REGEX = "\\p{Alnum}+";
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectTagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        if (!arePrefixesPresent(argMultimap, PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTagCommand.MESSAGE_USAGE));
        }

        String tag = argMultimap.getValue(PREFIX_TAG).orElse("");
        if (!isValidTagName(tag)) {
            throw new ParseException(MESSAGE_TAG_CONSTRAINTS);
        }
        Tag tagName = new Tag(tag.toLowerCase());


        return new SelectTagCommand(tagName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(TAG_VALIDATION_REGEX);
    }
}
