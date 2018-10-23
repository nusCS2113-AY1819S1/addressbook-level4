package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEFAULT_SELLING_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_ITEM;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddItemCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddItemCommandParser implements Parser<AddItemCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddItemCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DRINK_ITEM, PREFIX_DEFAULT_SELLING_PRICE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DRINK_ITEM, PREFIX_DEFAULT_SELLING_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddItemCommand.MESSAGE_USAGE));
        }

        String drinkName = ParserUtil.parseItemName(argMultimap.getValue(PREFIX_DRINK_ITEM).get());
        String defaultSellingPrice = ParserUtil.parseDefaultSellingPrice (
                                        argMultimap.getValue(PREFIX_DEFAULT_SELLING_PRICE).get());

        return new AddItemCommand (drinkName, defaultSellingPrice);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
