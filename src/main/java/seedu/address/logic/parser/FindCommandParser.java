package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.drink.Date;
import seedu.address.model.drink.Name;
import seedu.address.model.drink.NameContainsKeywordsPredicate;
import seedu.address.model.drink.Quantity;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE_BEFORE, PREFIX_DATE_AFTER,
                        PREFIX_QUANTITY_MORE, PREFIX_QUANTITY_LESS);
        String trimmedArgs = args.trim();
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE_BEFORE, PREFIX_DATE_AFTER,
                PREFIX_QUANTITY_MORE, PREFIX_QUANTITY_LESS)
                || !argMultimap.getPreamble().isEmpty() || trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_DRINK_NAME) != null) {
            Name drinkName = ParserUtil.parseDrinkName(argMultimap.getValue(PREFIX_DRINK_NAME).get());
            String[] nameKeywords = drinkName.toString().trim().split("\\s+");
            System.out.println("Name");
            return new FindCommand(new NameContainsKeywordsPredicate (Arrays.asList(nameKeywords)));
        } else if (argMultimap.getValue(PREFIX_DATE_BEFORE) != null) {
            Date dateBefore = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_BEFORE).get());
            System.out.println("DateB");
            return new FindCommand(dateBefore,false);
        } else if (argMultimap.getValue(PREFIX_DATE_AFTER) != null) {
            System.out.println("DateA");
            Date dateAfter = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_AFTER).get());
            return new FindCommand(dateAfter,true);
        } else if (argMultimap.getValue(PREFIX_QUANTITY_MORE) != null) {
            System.out.println("QuantityM");
            Quantity quantityMore = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY_MORE).get());
            return new FindCommand(quantityMore, true);
        } else {
            System.out.println("QuantityL");
            Quantity quantityLess = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY_LESS).get());
            return new FindCommand(quantityLess, false);
        }
    }

    /**
     * Returns true if at least one prefix is present in the
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
