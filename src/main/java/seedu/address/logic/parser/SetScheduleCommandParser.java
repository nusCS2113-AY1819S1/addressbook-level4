package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENUE;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.SetScheduleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.schedule.Schedule;

/**
 * Parses input arguments and creates a new SetScheduleCommand object
 */
public class SetScheduleCommandParser implements Parser<SetScheduleCommand> {

    /**
     * Parses the given {@code String args} of arguments in the context of the SetScheduleCommand
     * and returns an SetScheduleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetScheduleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME_START,
                PREFIX_TIME_END, PREFIX_VENUE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetScheduleCommand.MESSAGE_USAGE), pe);
        }

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (arePrefixesPresent(argMultimap, PREFIX_TIME_START, PREFIX_TIME_START, PREFIX_VENUE)) {
            Schedule schedule = ParserUtil.parseSchedule(
                    argMultimap.getValue(PREFIX_TIME_START).get(),
                    argMultimap.getValue(PREFIX_TIME_END).get(),
                    argMultimap.getValue(PREFIX_VENUE).get());

            editPersonDescriptor.setSchedule(schedule);
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(SetScheduleCommand.MESSAGE_SCHEDULE_FAIL + "\n"
                    + SetScheduleCommand.MESSAGE_USAGE);
        }

        return new SetScheduleCommand(index, editPersonDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
