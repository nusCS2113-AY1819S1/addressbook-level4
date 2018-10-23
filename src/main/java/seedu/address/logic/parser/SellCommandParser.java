package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_ITEM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.stream.Stream;

import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.parser.exceptions.ParseException;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class SellCommandParser implements Parser<SellCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SellCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DRINK_ITEM, PREFIX_DATE, PREFIX_QUANTITY, PREFIX_PRICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DRINK_ITEM, PREFIX_DATE, PREFIX_QUANTITY, PREFIX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE));
        }

        String drinkItem = ParserUtil.parseItemName (argMultimap.getValue (PREFIX_DRINK_ITEM).get());
        String date = argMultimap.getValue (PREFIX_DATE).get ();
        String quantitySold = argMultimap.getValue (PREFIX_QUANTITY).get();
        String totalRevenue = argMultimap.getValue (PREFIX_PRICE).get ();
        //        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        //        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        //        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        //        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        //        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        //        Person person = new Person(name, phone, email, address, tagList);

        return new SellCommand(drinkItem, date, quantitySold, totalRevenue);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
