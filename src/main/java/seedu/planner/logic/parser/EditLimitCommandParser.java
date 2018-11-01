package seedu.planner.logic.parser;
import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import java.util.stream.Stream;

import seedu.planner.logic.commands.EditLimitCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.MoneyFlow;


/**
 * This command Parser is very similar to the @AddLimitCommandParser.
 * Will return the editLimitCommand with the given limit.
 */
public class EditLimitCommandParser implements Parser <EditLimitCommand> {
    /**
     * Parse the EditLimitCommand like the Limit command, return the editLimitCommand
     * with the input of limit.
     */

    private String [] datesIn; //the string is used to divide two the whole strings into two substrings.

    @Override
    public EditLimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_MONEYFLOW);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_MONEYFLOW)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditLimitCommand.MESSAGE_USAGE));
        }
        MoneyFlow money = ParserUtil.parseMoneyFlow("-" + argMultimap.getValue(PREFIX_MONEYFLOW).get());
        datesIn = argMultimap.getValue(PREFIX_DATE).get().split("\\s+");

        Date dateStart = ParserUtil.parseDate(datesIn[0]);
        Date dateEnd = ParserUtil.parseDate(datesIn[1]);
        Limit limit = new Limit(dateStart, dateEnd, money);


        return new EditLimitCommand(limit);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
