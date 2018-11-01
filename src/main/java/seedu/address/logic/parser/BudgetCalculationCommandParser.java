package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_BUDGET;

import java.util.stream.Stream;

import seedu.address.logic.commands.BudgetCalculationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.clubbudget.TotalBudget;

/**
 * Parses input arguments and creates a new BudgetCalculationCommand object
 */
public class BudgetCalculationCommandParser implements Parser<BudgetCalculationCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the BudgetCalculationCommand
     * and returns an BudgetCalculationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public BudgetCalculationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TOTAL_BUDGET);

        if (!arePrefixesPresent(argMultimap, PREFIX_TOTAL_BUDGET)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    BudgetCalculationCommand.MESSAGE_USAGE));
        }

        TotalBudget totalBudget = ParserUtil.parseTotalBudget(argMultimap.getValue(PREFIX_TOTAL_BUDGET).get());

        return new BudgetCalculationCommand(totalBudget);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
