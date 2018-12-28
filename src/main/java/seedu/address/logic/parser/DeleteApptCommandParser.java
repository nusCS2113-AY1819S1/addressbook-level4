package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_START;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appt.ApptDateTime;

//@@author brandonccm1996
/**
 * Parses input arguments and creates a new {@code DeleteApptCommand} object
 */
public class DeleteApptCommandParser implements Parser<DeleteApptCommand> {

    @Override
    public DeleteApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPT_START);

        if (!arePrefixesPresent(argMultimap, PREFIX_APPT_START)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteApptCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteApptCommand.MESSAGE_USAGE),
                    ive);
        }

        ApptDateTime start = ParserUtil.parseApptDateTime(argMultimap.getValue(PREFIX_APPT_START).get());
        return new DeleteApptCommand(index, start);
    }
}
