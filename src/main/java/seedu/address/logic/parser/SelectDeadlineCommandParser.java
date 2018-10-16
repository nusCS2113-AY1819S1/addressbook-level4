//@@author emobeany

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.SelectDeadlineCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.task.Deadline;

/**
 * Parses input arguments and creates a new SelectDeadlineCommand object
 */
public class SelectDeadlineCommandParser implements Parser<SelectDeadlineCommand> {

    @Override
    public SelectDeadlineCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_DAY, PREFIX_MONTH, PREFIX_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectDeadlineCommand.MESSAGE_USAGE));
        }

        String day = ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get());
        String month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());
        String year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());

        Deadline deadline = new Deadline(day, month, year);

        return new SelectDeadlineCommand(deadline);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
