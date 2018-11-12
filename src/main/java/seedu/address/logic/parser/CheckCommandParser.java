package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.CheckCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Mode;

//@@author pinjuen

/**
 * Parses input arguments and creates a new {@code CheckCommand} object
 */
public class CheckCommandParser implements Parser<CheckCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code CheckCommand}
     * and returns a {@code CheckCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        if (!didPrefixAppearOnlyOnce(args, PREFIX_MODE.toString())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE));
        }

        Mode mode = ParserUtil.parseMode(argMultimap.getValue(PREFIX_MODE).get());

        return new CheckCommand(mode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks whether prefixes appeared more than once within the argument
     */
    public boolean didPrefixAppearOnlyOnce(String argument, String prefix) {
        String precedeWhitespacePrefix = " " + prefix;
        return argument.indexOf(precedeWhitespacePrefix) == argument.lastIndexOf(precedeWhitespacePrefix);
    }
}


