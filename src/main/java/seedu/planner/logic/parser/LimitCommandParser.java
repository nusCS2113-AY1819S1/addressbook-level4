package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import java.util.stream.Stream;

import seedu.planner.logic.commands.LimitCommand;

import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;

import seedu.planner.model.record.MoneyFlow;



/**
* The Parser will parse those values in one format Limit and return back to LimitCommand.
* */
public class LimitCommandParser implements Parser<LimitCommand> {
    /**
     * Parses the information required for the limit command.
     * and returns a limit object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    private String [] datesIn; //the string is used to divide two the whole strings into two substrings.

    @Override
    public LimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_MONEYFLOW);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_MONEYFLOW)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LimitCommand.MESSAGE_USAGE));
        }
        //TODO: change the scan function to read only the integer, add a "-" manually before the integer.
        MoneyFlow money = ParserUtil.parseMoneyFlow("-" + argMultimap.getValue(PREFIX_MONEYFLOW).get());
        datesIn = argMultimap.getValue(PREFIX_DATE).get().split("\\s+");

        Date dateStart = ParserUtil.parseDate(datesIn[0]);
        Date dateEnd = ParserUtil.parseDate(datesIn[1]);
        Limit limit = new Limit(dateStart, dateEnd, money);


        return new LimitCommand(limit);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
