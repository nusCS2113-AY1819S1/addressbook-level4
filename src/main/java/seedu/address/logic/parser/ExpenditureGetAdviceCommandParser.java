//@@author SHININGGGG
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERIOD;

import java.util.stream.Stream;

import seedu.address.logic.commands.ExpenditureGetAdviceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenditureinfo.Money;

/**
 * Parses input arguments and creates a new ExpenditureGetAdviceCommand object
 */
public class ExpenditureGetAdviceCommandParser implements Parser<ExpenditureGetAdviceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExpenditureGetAdviceCommand
     * and returns an ExpenditureGetAdviceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ExpenditureGetAdviceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONEY, PREFIX_PERIOD);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONEY, PREFIX_PERIOD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ExpenditureGetAdviceCommand.MESSAGE_USAGE));
        }

        Money money = ParserUtil.parseMoney(argMultimap.getValue(PREFIX_MONEY).get());

        String m = money.addingMoney;
        String n = argMultimap.getValue(PREFIX_PERIOD).get();

        return new ExpenditureGetAdviceCommand(m, n);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
