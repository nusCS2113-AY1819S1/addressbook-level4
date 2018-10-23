package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ISBN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SellCommand;
import seedu.address.logic.commands.SellCommand.DecreaseQuantity;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Quantity;

/**
 * Parses input arguments and creates a new SellCommand object
 */
public class SellCommandParser implements Parser<SellCommand> {

    /**
     * Parses the given (@code String} of arguments in the context of the SellCommand
     * and returns a SellCommand object for execution.
     * @throw ParseException if the user input does not conform the expected format
     */
    public SellCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ISBN, PREFIX_QUANTITY);

        String findBookBy;
        Index index;
        Isbn isbn;

        if (!argMultimap.getValue(PREFIX_ISBN).isPresent()) {
            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE), pe);
            }
            findBookBy = Integer.toString(index.getZeroBased());
        } else {
            try {
                isbn = ParserUtil.parseIsbn(argMultimap.getValue(PREFIX_ISBN).get());
            } catch (ParseException pe) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SellCommand.MESSAGE_USAGE), pe);
            }
            findBookBy = isbn.value;
        }

        DecreaseQuantity decreaseQuantity = new DecreaseQuantity();
        if (argMultimap.getValue(PREFIX_QUANTITY).isPresent()) {
            Quantity quantity = ParserUtil.parseQuantity(argMultimap.getValue(PREFIX_QUANTITY).get());
            decreaseQuantity.setQuantity(quantity);
        }

        if (!decreaseQuantity.isQuantityFieldSpecified()) {
            throw new ParseException(SellCommand.MESSAGE_NOT_SOLD);
        }


        return new SellCommand(findBookBy, decreaseQuantity);
    }
}
