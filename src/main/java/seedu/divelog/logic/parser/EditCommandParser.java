package seedu.divelog.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.divelog.commons.core.Messages;
import seedu.divelog.commons.core.index.Index;
import seedu.divelog.logic.commands.EditCommand;
import seedu.divelog.logic.parser.exceptions.ParseException;
import seedu.divelog.model.dive.Location;
import seedu.divelog.model.dive.OurDate;
import seedu.divelog.model.dive.Time;
import seedu.divelog.model.dive.TimeZone;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    //@@author arjo129
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        CliSyntax.PREFIX_DATE_START,
                        CliSyntax.PREFIX_TIME_START,
                        CliSyntax.PREFIX_DATE_END,
                        CliSyntax.PREFIX_TIME_END,
                        CliSyntax.PREFIX_SAFETY_STOP,
                        CliSyntax.PREFIX_DEPTH,
                        CliSyntax.PREFIX_LOCATION,
                        CliSyntax.PREFIX_TIMEZONE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditCommand.EditDiveDescriptor editDiveSessionDescriptor = new EditCommand.EditDiveDescriptor();

        if (argMultimap.getValue(CliSyntax.PREFIX_DATE_START).isPresent()) {
            editDiveSessionDescriptor.setDateStart(new OurDate(argMultimap.getValue(CliSyntax.PREFIX_DATE_START)
                    .get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_TIME_START).isPresent()) {
            editDiveSessionDescriptor.setStart(new Time(argMultimap.getValue(CliSyntax.PREFIX_TIME_START).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_SAFETY_STOP).isPresent()) {
            editDiveSessionDescriptor.setSafetyStop(new Time(argMultimap.getValue(CliSyntax.PREFIX_SAFETY_STOP).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_DATE_END).isPresent()) {
            editDiveSessionDescriptor.setDateEnd(new OurDate(argMultimap.getValue(CliSyntax.PREFIX_DATE_END).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_TIME_END).isPresent()) {
            editDiveSessionDescriptor.setEnd(new Time(argMultimap.getValue(CliSyntax.PREFIX_TIME_END).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_DEPTH).isPresent()) {
            editDiveSessionDescriptor.setDepthProfile(
                    ParserUtil.parseDepth(argMultimap.getValue(CliSyntax.PREFIX_DEPTH).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_LOCATION).isPresent()) {
            editDiveSessionDescriptor.setLocation(
                    new Location(argMultimap.getValue(CliSyntax.PREFIX_LOCATION).get()));
        }
        if (argMultimap.getValue(CliSyntax.PREFIX_TIMEZONE).isPresent()) {
            editDiveSessionDescriptor.setTimeZone(
                   new TimeZone(argMultimap.getValue(CliSyntax.PREFIX_TIMEZONE).get()));
        }
        if (!editDiveSessionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editDiveSessionDescriptor);
    }

}
