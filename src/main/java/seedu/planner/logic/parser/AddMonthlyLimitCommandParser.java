package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.commands.AddMonthlyLimitCommand.DATE_SPECIAL_FOR_MONTHLY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import java.util.stream.Stream;

import seedu.planner.logic.commands.AddMonthlyLimitCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.MoneyFlow;
//@@Author OscarZeng
/**
 * This parser will read the moneyFlow and generate a limit with special dates,
 * whose year are 0001 while days are the end or the month.
 */
public class AddMonthlyLimitCommandParser implements Parser<AddMonthlyLimitCommand> {
    private String moneyString;
    private Date dateSpecial;

    @Override
    public AddMonthlyLimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONEYFLOW);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONEYFLOW)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMonthlyLimitCommand.MESSAGE_USAGE));
        }
        moneyString = "-" + argMultimap.getValue(PREFIX_MONEYFLOW).get();

        if (!(MoneyFlow.isValidMoneyFlow(moneyString))) {
            throw new ParseException("The limit money can only be normal real number. \n"
                    + "Example: m/100.");
        }
        MoneyFlow money = ParserUtil.parseMoneyFlow(moneyString);
        dateSpecial = ParserUtil.parseDate(DATE_SPECIAL_FOR_MONTHLY);
        Limit limit = new Limit(dateSpecial, dateSpecial, money);

        return new AddMonthlyLimitCommand(limit);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
