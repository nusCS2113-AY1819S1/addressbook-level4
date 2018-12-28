package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPT_DATE;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.logic.commands.ClearApptsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appt.ApptDate;

//@@author brandonccm1996
/**
 * Parses input arguments and creates a new {@code ClearApptsCommand} object
 */
public class ClearApptsCommandParser implements Parser<ClearApptsCommand> {
    @Override
    public ClearApptsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_APPT_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_APPT_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearApptsCommand.MESSAGE_USAGE));
        }

        ApptDate date = ParserUtil.parseApptDate(argMultimap.getValue(PREFIX_APPT_DATE).get());
        return new ClearApptsCommand(date);
    }
}
