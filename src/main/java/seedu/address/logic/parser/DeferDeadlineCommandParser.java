package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeferDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@ChanChunCheong
/**
 * Parses input arguments and creates a new DeferDeadlineCommand object
 */
public class DeferDeadlineCommandParser implements Parser<DeferDeadlineCommand> {
    public static final String MESSAGE_INVALID_DEFERRED_DAYS_EXCEEDED = "Deferred Days need to be positive integer and "
            + "less than 32";
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeferDeadlineCommand}
     * and returns a {@code DeferDeadlineCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeferDeadlineCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DAY);
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_DAY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeferDeadlineCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
        String day = argMultimap.getValue(PREFIX_DAY).orElse("");
        int deferredDay = ParserUtil.parseDefferedDays(day);
        if (deferredDay < 1 || deferredDay > 31) {
            throw new ParseException(MESSAGE_INVALID_DEFERRED_DAYS_EXCEEDED);
        }
        return new DeferDeadlineCommand(index, deferredDay);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
