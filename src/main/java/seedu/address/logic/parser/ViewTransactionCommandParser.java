package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * This class parses the arguments to view a given transaction's details.
 */
public class ViewTransactionCommandParser implements Parser<ViewTransactionCommand> {

    @Override
    public ViewTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TIME);
        if (!arePrefixesPresent(argMultimap, PREFIX_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException("No transaction time provided!");
        }
        String transactionTime = ParserUtil.parseDateAndTime(argMultimap.getValue(PREFIX_TIME).get());
        return new ViewTransactionCommand(transactionTime);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
