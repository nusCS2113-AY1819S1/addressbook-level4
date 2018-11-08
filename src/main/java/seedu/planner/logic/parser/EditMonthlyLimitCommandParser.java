package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_MONEYFLOW;

import java.util.stream.Stream;

import seedu.planner.logic.commands.EditMonthlyLimitCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.record.Date;
import seedu.planner.model.record.Limit;
import seedu.planner.model.record.MoneyFlow;
//@@Author OscarZeng

/**
 * This parser do the similar function as AddMonthlyLimitCommandParser
 * generate the limit with the special date.
 */
public class EditMonthlyLimitCommandParser implements Parser<EditMonthlyLimitCommand> {
    public static final String DATE_SPECIAL_FOR_MONTHLY = "01-01-9999";
    private String moneyString;
    private Date dateSpecial;

    @Override
    public EditMonthlyLimitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONEYFLOW);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONEYFLOW)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditMonthlyLimitCommand.MESSAGE_USAGE));
        }
        moneyString = "-" + argMultimap.getValue(PREFIX_MONEYFLOW).get();

        if (!(MoneyFlow.isValidMoneyFlow(moneyString))) {
            throw new ParseException("The limit money can only be normal real number. \n"
                    + "Example: m/100.");
        }
        MoneyFlow money = ParserUtil.parseMoneyFlow(moneyString);
        dateSpecial = ParserUtil.parseDate(DATE_SPECIAL_FOR_MONTHLY);
        Limit limit = new Limit(dateSpecial, dateSpecial, money);

        return new EditMonthlyLimitCommand(limit);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
