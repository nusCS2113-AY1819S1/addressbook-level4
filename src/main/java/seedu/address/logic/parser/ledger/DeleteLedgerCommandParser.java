package seedu.address.logic.parser.ledger;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.ledger.DeleteLedgerCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ledger.DateLedger;

/**
 * Parses input arguments and creates a new DeleteItemCommand object
 */
public class DeleteLedgerCommandParser implements Parser<DeleteLedgerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteItemCommand
     * and returns an DeleteItemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteLedgerCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE) /*|| argMultimap.getPreamble().isEmpty()*/) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteLedgerCommand.MESSAGE_USAGE));
        }
        DateLedger date = ParserUtil.parseDateLedger(argMultimap.getValue(PREFIX_DATE).get());
        return new DeleteLedgerCommand(date);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
