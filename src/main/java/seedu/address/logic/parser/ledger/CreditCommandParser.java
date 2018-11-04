package seedu.address.logic.parser.ledger;

import seedu.address.logic.commands.ledger.CreditCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ledger.Account;
import seedu.address.model.ledger.DateLedger;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BALANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

public class CreditCommandParser implements Parser<CreditCommand> {

    public CreditCommand parse (String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_BALANCE);

        if(!arePrefixesPresent(argumentMultimap, PREFIX_DATE, PREFIX_BALANCE) ||
                !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreditCommand.MESSAGE_USAGE));
        }
        DateLedger dateLedger = ParserUtil.parseDateLedger(argumentMultimap.getValue(PREFIX_DATE).get());
        Double amount = ParserUtil.parseBalance(argumentMultimap.getValue(PREFIX_BALANCE).get());

        return new CreditCommand(dateLedger, amount);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
