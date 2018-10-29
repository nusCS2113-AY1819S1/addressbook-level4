package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.FinishedReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the arguments for finishing a reminder task.
 */
public class FinishedReminderCommandParser implements Parser<FinishedReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FinishedReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {

            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FinishedReminderCommand.MESSAGE_USAGE));
        }

        String toRemoveReminderTime;
        try {
            toRemoveReminderTime = ParserUtil.parseReminderTime(argMultimap.getValue(PREFIX_TIME).get());
        } catch (ParseException e) {
            throw e;
        }
        return new FinishedReminderCommand(toRemoveReminderTime);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
