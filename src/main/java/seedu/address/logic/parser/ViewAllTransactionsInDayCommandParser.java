package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewAllTransactionsInDayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class is the parser for the ViewAllTransactionsInDay command
 */
public class ViewAllTransactionsInDayCommandParser implements Parser<ViewAllTransactionsInDayCommand> {

    @Override
    public ViewAllTransactionsInDayCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME);
        if (!arePrefixesPresent(argMultimap, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewAllTransactionsInDayCommand.COMMAND_WORD
                                    + ViewAllTransactionsInDayCommand.MESSAGE_USAGE));
        }
        String date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_TIME).get());
        return new ViewAllTransactionsInDayCommand(date);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
