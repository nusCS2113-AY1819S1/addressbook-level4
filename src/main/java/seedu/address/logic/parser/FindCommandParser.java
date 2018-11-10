package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DRINK_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.drink.*;

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
                ArgumentTokenizer.tokenize(args, PREFIX_DRINK_NAME, PREFIX_DATE, PREFIX_QUANTITY);
        String trimmedArgs = args.trim();
        if (!arePrefixesPresent(argMultimap, PREFIX_DRINK_NAME, PREFIX_DATE, PREFIX_QUANTITY)
                || !argMultimap.getPreamble().isEmpty() || trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_DRINK_NAME).isPresent()) {
            Name drinkName = ParserUtil.parseDrinkName(argMultimap.getValue(PREFIX_DRINK_NAME).get());
            String[] nameKeywords = drinkName.toString().trim().split("\\s+");
            System.out.println("Name");
            return new FindCommand(new NameContainsKeywordsPredicate (Arrays.asList(nameKeywords)));
        } else if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            Date dateBefore = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
            System.out.println("Date");
            return new FindCommand(new DateCompareBeforePredicate(dateBefore));
        } else if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            System.out.println("Quantity");
            Quantity quantityLess = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
            return new FindCommand(new QuantityCompareLessPredicate(quantityLess));
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
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
