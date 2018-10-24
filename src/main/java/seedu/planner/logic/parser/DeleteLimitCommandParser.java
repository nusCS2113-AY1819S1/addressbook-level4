package seedu.planner.logic.parser;

import seedu.planner.logic.commands.DeleteLimitCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;

import java.util.stream.Stream;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;

/**
 *
 */
public class DeleteLimitCommandParser implements Parser<DeleteLimitCommand>{
    /**
     * Parses the information required for the limit command.
     * and returns a limit object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    private String [] datesIn; //the string is used to divide two the whole strings into two substrings.

    @Override
    public DeleteLimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLimitCommand.MESSAGE_USAGE));
        }

        datesIn = argMultimap.getValue(PREFIX_DATE).get().split("\\s+");

        Date dateStart = ParserUtil.parseDate(datesIn[0]);
        Date dateEnd = ParserUtil.parseDate(datesIn[1]);
        Limit limit = new Limit(dateStart, dateEnd, ParserUtil.parseMoneyFlow("-1"));


        return new DeleteLimitCommand(limit);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
