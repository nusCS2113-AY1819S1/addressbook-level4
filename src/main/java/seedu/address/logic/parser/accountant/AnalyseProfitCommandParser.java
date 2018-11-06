//@@author liu-tianhang
package seedu.address.logic.parser.accountant;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.analysis.AnalysisPeriodType;
import seedu.address.logic.commands.accountant.AnalyseProfitCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Command for analyseProfit
 */
public class AnalyseProfitCommandParser implements Parser<AnalyseProfitCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AnalyseCostsCommand
     * and returns an AnalyseCostsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AnalyseProfitCommand parse(String args) throws ParseException {
        AnalysisPeriodType period;
        if (args.length () > 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnalyseProfitCommand.MESSAGE_USAGE));
        }
        period = AnalysisPeriodType.getPeriod(args);

        return new AnalyseProfitCommand (period);
    }
}
