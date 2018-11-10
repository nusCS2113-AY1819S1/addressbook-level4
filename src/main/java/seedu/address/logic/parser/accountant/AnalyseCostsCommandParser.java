//@@author liu-tianhang
package seedu.address.logic.parser.accountant;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.analysis.AnalysisPeriodType;
import seedu.address.logic.commands.accountant.AnalyseCostsCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AnalyseCostsCommand object
 */
public class AnalyseCostsCommandParser implements Parser<AnalyseCostsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AnalyseCostsCommand
     * and returns an AnalyseCostsCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AnalyseCostsCommand parse(String args) throws ParseException {
        AnalysisPeriodType period;
        if (args.length() > 3) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnalyseCostsCommand.MESSAGE_USAGE));
        }

        period = ParserUtil.parseAnalysisPeriod(args);

        return new AnalyseCostsCommand(period);
    }

}
