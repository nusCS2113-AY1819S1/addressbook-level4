package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_MESSAGE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.timeidentifiedclass.exceptions.InvalidTimeFormatException;
import seedu.address.model.timeidentifiedclass.shopday.Reminder;


/**
 * Parses input arguments and creates new AddReminderCommand object.
 */

public class AddReminderCommandParser implements Parser<AddReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of AddReminderCommand
     * and returns an AddReminderCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME, PREFIX_REMINDER_MESSAGE);
        if (!arePrefixesPresent(argMultimap, PREFIX_TIME,PREFIX_REMINDER_MESSAGE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        Reminder reminder;
        try {
            reminder = new Reminder(ParserUtil.parseReminderTime(argMultimap.getValue(PREFIX_TIME).get()),
                    argMultimap.getValue(PREFIX_REMINDER_MESSAGE).get());
        } catch (InvalidTimeFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Reminder.REMINDER_TIME_CONSTRAINTS));
        }

        return new AddReminderCommand(reminder);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
