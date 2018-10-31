package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.TimeType;


/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_MONTH, PREFIX_YEAR);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        }

        boolean hasDate = argMultimap.getValue(PREFIX_DATE).isPresent();
        boolean hasMonth = argMultimap.getValue(PREFIX_MONTH).isPresent();
        boolean hasYear = argMultimap.getValue(PREFIX_YEAR).isPresent();

        if (!hasDate && !hasMonth && !hasYear) {
            return new SelectCommand(index);
        } else if (hasDate && !hasMonth && !hasYear) {
            EventDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            return new SelectCommand(index, date.eventDate, TimeType.DAY);
        } else if (hasMonth && !hasYear) {
            String month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
            return new SelectCommand(index, month, TimeType.MONTH);
        } else if (!hasMonth && hasYear) {
            String year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
            return new SelectCommand(index, year, TimeType.YEAR);
        } else if (hasMonth && hasYear) {
            String month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
            String year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
            String combined = year + "-" + month;
            return new SelectCommand(index, combined, TimeType.MONTH_AND_YEAR);
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

    }
}
