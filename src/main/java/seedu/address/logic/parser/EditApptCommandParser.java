package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DRNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_INFO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_ORIGINAL_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_VENUE;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditApptCommand;
import seedu.address.logic.commands.EditApptCommand.EditApptDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appt.ApptDateTime;

//@@author brandonccm1996
/**
 * Parses input arguments and creates a new EditApptCommand object
 */
public class EditApptCommandParser implements Parser<EditApptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditApptCommand
     * and returns an EditApptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPT_ORIGINAL_START, PREFIX_APPT_START,
                PREFIX_APPT_END, PREFIX_APPT_VENUE, PREFIX_APPT_INFO, PREFIX_APPT_DRNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_APPT_ORIGINAL_START)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditApptCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditApptCommand.MESSAGE_USAGE), pe);
        }

        ApptDateTime originalStart = ParserUtil.parseApptDateTime
                (argMultimap.getValue(PREFIX_APPT_ORIGINAL_START).get());

        EditApptDescriptor editApptDescriptor = new EditApptDescriptor();
        if (argMultimap.getValue(PREFIX_APPT_START).isPresent()) {
            editApptDescriptor.setStart(ParserUtil.parseApptDateTime(argMultimap.getValue(PREFIX_APPT_START).get()));
        }
        if (argMultimap.getValue(PREFIX_APPT_END).isPresent()) {
            editApptDescriptor.setEnd(ParserUtil.parseApptDateTime(argMultimap.getValue(PREFIX_APPT_END).get()));
        }
        if (argMultimap.getValue(PREFIX_APPT_VENUE).isPresent()) {
            editApptDescriptor.setVenue(ParserUtil.parseApptVenue(argMultimap.getValue(PREFIX_APPT_VENUE).get()));
        }
        if (argMultimap.getValue(PREFIX_APPT_INFO).isPresent()) {
            editApptDescriptor.setInfo(ParserUtil.parseApptInfo(argMultimap.getValue(PREFIX_APPT_INFO).get()));
        }
        if (argMultimap.getValue(PREFIX_APPT_DRNAME).isPresent()) {
            editApptDescriptor.setDrName(ParserUtil.parseApptDrName(argMultimap.getValue(PREFIX_APPT_DRNAME).get()));
        }
        if (!editApptDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditApptCommand.MESSAGE_NOT_EDITED);
        }

        return new EditApptCommand(index, originalStart, editApptDescriptor);
    }
}
