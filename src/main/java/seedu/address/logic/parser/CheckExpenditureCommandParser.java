//@@author feijunzi
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START;

import java.util.stream.Stream;

import seedu.address.logic.commands.CheckExpenditureCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expenditureinfo.Date;

/**
 * Parses input arguments and creates a new CheckExpenditureCommand object
 */
public class CheckExpenditureCommandParser implements Parser<CheckExpenditureCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckExpenditureCommand
     * and returns an CheckExpenditure object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CheckExpenditureCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_START, PREFIX_END);

        if (!arePrefixesPresent(argMultimap, PREFIX_START, PREFIX_END)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    CheckExpenditureCommand.MESSAGE_USAGE));
        }
        Date date1 = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START).get());
        Date date2 = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END).get());

        String d1 = date1.addingDate;
        String d2 = date2.addingDate;

        if (Integer.parseInt(d1.substring(6)) > Integer.parseInt(d2.substring(6))) {
            throw new ParseException("date2 should larger than date 1");
        } else if (Integer.parseInt(d1.substring(6)) == Integer.parseInt(d2.substring(6))) {
            if (Integer.parseInt(d1.substring(3, 5)) > Integer.parseInt(d2.substring(3, 5))) {
                throw new ParseException("date2 should larger than date 1");
            } else if ((Integer.parseInt(d1.substring(3, 5)) == Integer.parseInt(d2.substring(3, 5)))
                    && (Integer.parseInt(d1.substring(0, 2)) > Integer.parseInt(d2.substring(0, 2)))) {
                throw new ParseException("date2 should larger than date 1");
            }
        }
        return new CheckExpenditureCommand(d1, d2);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}



